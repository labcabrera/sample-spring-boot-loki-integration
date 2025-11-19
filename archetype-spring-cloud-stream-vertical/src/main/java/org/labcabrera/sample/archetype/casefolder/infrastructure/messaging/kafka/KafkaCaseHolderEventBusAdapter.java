package org.labcabrera.sample.archetype.casefolder.infrastructure.messaging.kafka;

import java.time.Instant;
import java.util.UUID;

import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderEventBusPort;
import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderCreatedEvent;
import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderDeletedEvent;
import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderUpdatedEvent;
import org.labcabrera.sample.archetype.shared.application.SecurityPort;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
@Slf4j
public class KafkaCaseHolderEventBusAdapter implements CaseFolderEventBusPort {

    private final StreamBridge streamBridge;
    private final SecurityPort securityPort;

    @Override
    public void publish(CaseFolderCreatedEvent event) {
        sendNotification("caseFolderCreated-out-0", event);
    }

    @Override
    public void publish(CaseFolderUpdatedEvent event) {
        sendNotification("caseFolderCreated-out-0", event);
    }

    @Override
    public void publish(CaseFolderDeletedEvent event) {
        sendNotification("caseFolderCreated-out-0", event);
    }

    private void sendNotification(String binding, Object event) {
        try {
            var user = securityPort.requireCurrentUser();
            String correlationId = UUID.randomUUID().toString();
            Message<Object> message = MessageBuilder
                .withPayload(event)
                .setHeader("contentType", "application/json")
                .setHeader("x-correlation-id", correlationId)
                .setHeader("x-origin", "case-service")
                .setHeader("x-sent-at", Instant.now().toString())
                .setHeader("x-username", user.username())
                .setHeader("x-roles", String.join(",", user.roles()))
                .build();
            streamBridge.send(binding, message);
        }
        catch (Exception ex) {
            log.error("Failed to publish {}", event.getClass().getSimpleName(), ex);
        }
    }

}
