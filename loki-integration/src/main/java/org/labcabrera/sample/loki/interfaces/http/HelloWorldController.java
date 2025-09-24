package org.labcabrera.sample.loki.interfaces.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/counters")
public class HelloWorldController {

    private Map<String, Integer> counterMap = new ConcurrentHashMap<>();

    @GetMapping
    public Mono<Map<String, Integer>> sayHello() {
        return Mono.just(counterMap);
    }

    @PostMapping("/{key}")
    public Mono<Integer> incrementCounter(@PathVariable String key) {
        return Mono.fromCallable(() -> 
            counterMap.compute(key, (k, v) -> (v == null) ? 1 : v + 1));
    }

    @PutMapping("/{key}/{value}")
    public Mono<Integer> setCounter(@PathVariable("key") String key, @PathVariable("value") Integer value) {
        return Mono.fromCallable(() -> 
            counterMap.put(key, value)
        );
    }

}
