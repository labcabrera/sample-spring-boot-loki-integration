package org.labcabrera.sample.archetype.shared.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SimpleQueryBus implements QueryBus {

    private final Map<Class<?>, QueryHandler<?, ?>> handlers = new HashMap<>();

    public <Q, R> void registerHandler(Class<Q> queryClass, QueryHandler<Q, R> handler) {
        log.debug("Registering handler for query: {}", queryClass.getSimpleName());
        handlers.put(queryClass, handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R dispatch(Object query) {
        log.debug("Dispatching query: {}", query.getClass().getSimpleName());

        QueryHandler<Object, R> handler = (QueryHandler<Object, R>) handlers.get(query.getClass());

        if (handler == null) {
            throw new IllegalStateException("No handler registered for query: " + query.getClass().getName());
        }

        return handler.handle(query);
    }

}
