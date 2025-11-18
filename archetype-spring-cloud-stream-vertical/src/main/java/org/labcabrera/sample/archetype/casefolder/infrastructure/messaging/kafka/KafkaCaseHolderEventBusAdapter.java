package org.labcabrera.sample.archetype.casefolder.infrastructure.messaging.kafka;

import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderEventBusPort;
import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderCreatedEvent;
import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderDeletedEvent;
import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderUpdatedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaCaseHolderEventBusAdapter implements CaseFolderEventBusPort {

    private final StreamBridge streamBridge;

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
            streamBridge.send(binding, event);
        }
        catch (Exception ex) {
            log.error("Failed to publish {}", event.getClass().getSimpleName(), ex);
        }
    }

}
