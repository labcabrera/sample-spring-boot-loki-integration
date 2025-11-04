package org.labcabrera.sample.loki.interfaces.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Tag(name = "Counters", description = "Micrometer Sample Management API")
@RestController
@Slf4j
@RequestMapping("/api/v1/counters")
public class CounterController implements CounterControllerDefinition {

    private Map<String, Integer> counterMap = new ConcurrentHashMap<>();

    private final Counter counterOperations;
    private final Counter counterFoo;
    private final Counter counterError;

    public CounterController(MeterRegistry meterRegistry) {
        this.counterOperations = Counter.builder("counter.operations.total")
            .description("Total number of operations")
            .register(meterRegistry);
        this.counterFoo = Counter.builder("counter.foo.total")
            .description("Total number of foo operations")
            .tag("type", "example")
            .register(meterRegistry);
        this.counterError = Counter.builder("counter.errors.total")
            .description("Total number of error operations")
            .register(meterRegistry);
        Gauge.builder("foo.count", counterMap, e -> e.getOrDefault("foo", 0))
            .description("Current foo count")
            .register(meterRegistry);
    }

    @GetMapping
    @Operation(summary = "Get all counters", description = "Returns all counters with current values")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK")
    })
    public Mono<Map<String, Integer>> getCounters() {
        log.debug("Getting all counters");
        return Mono.just(counterMap);
    }

    @PostMapping("/{key}")
    @Operation(summary = "Increment counter", description = "Increment the counter identified by the given key")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "New counter value"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    public Mono<Integer> incrementCounter(@Parameter(description = "Counter key to increment", required = true) @PathVariable String key) {
        log.info("Incrementing counter for key {}", key);
        if ("err".equals(key)) {
            counterError.increment();
            throw new RuntimeException("Simulated error for key 'err'");
        }
        return Mono.fromCallable(() -> {
            counterOperations.increment();
            counterMap.compute(key, (k, v) -> (v == null) ? 1 : v + 1);
            if ("foo.counter.total".equals(key)) {
                counterFoo.increment();
            }
            return counterMap.get(key);
        });
    }

    @PutMapping("/{key}/{value}")
    @Operation(summary = "Set counter value", description = "Set the counter value for a specific key")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Previous counter value returned"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public Mono<Integer> setCounter(@Parameter(description = "Counter key", required = true) @PathVariable("key") String key,
                                    @Parameter(description = "Value to set", required = true) @PathVariable("value") Integer value) {
        log.info("Setting counter for key {} to value {}", key, value);
        return Mono.fromCallable(() -> counterMap.put(key, value));
    }

}
