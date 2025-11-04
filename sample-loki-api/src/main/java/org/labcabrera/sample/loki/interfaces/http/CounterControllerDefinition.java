package org.labcabrera.sample.loki.interfaces.http;

import java.util.Map;

import reactor.core.publisher.Mono;

public interface CounterControllerDefinition {

    Mono<Map<String, Integer>> getCounters();

    Mono<Integer> incrementCounter(String key);

    Mono<Integer> setCounter(String key, Integer value);

}
