package org.labcabrera.sample.loki.interfaces.http;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Mono;

public interface CounterControllerDefinition {

    @GetMapping
    @Operation(summary = "Get all counters", description = "Returns all counters with current values")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK")
    })
    Mono<Map<String, Integer>> getCounters();

    @PostMapping("/{key}")
    @Operation(summary = "Increment counter", description = "Increment the counter identified by the given key")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "New counter value"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })
    Mono<Integer> incrementCounter(String key);

    @PutMapping("/{key}/{value}")
    @Operation(summary = "Set counter value", description = "Set the counter value for a specific key")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Previous counter value returned"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    Mono<Integer> setCounter(String key, Integer value);

}
