package org.labcabrera.sample.loki.interfaces.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Override
    public Mono<Map<String, Integer>> getCounters() {
        log.debug("Getting all counters");
        return Mono.just(counterMap);
    }

    @Override
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

    @Override
    public Mono<Integer> setCounter(@Parameter(description = "Counter key", required = true) @PathVariable("key") String key,
        @Parameter(description = "Value to set", required = true) @PathVariable("value") Integer value) {
        log.info("Setting counter for key {} to value {}", key, value);
        return Mono.fromCallable(() -> counterMap.put(key, value));
    }

}
