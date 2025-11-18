package org.labcabrera.sample.archetype.application.cqrs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SimpleCommandBus implements CommandBus {

    private final Map<Class<?>, CommandHandler<?, ?>> handlers = new HashMap<>();

    public <C, R> void registerHandler(Class<C> commandClass, CommandHandler<C, R> handler) {
        log.debug("Registering handler for command: {}", commandClass.getSimpleName());
        handlers.put(commandClass, handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R dispatch(Object command) {
        log.debug("Dispatching command: {}", command.getClass().getSimpleName());

        CommandHandler<Object, R> handler = (CommandHandler<Object, R>) handlers.get(command.getClass());

        if (handler == null) {
            throw new IllegalStateException("No handler registered for command: " + command.getClass().getName());
        }

        return handler.handle(command);
    }

}
