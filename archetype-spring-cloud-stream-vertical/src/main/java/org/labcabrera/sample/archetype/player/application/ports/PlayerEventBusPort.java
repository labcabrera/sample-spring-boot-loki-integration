package org.labcabrera.sample.archetype.player.application.ports;

import org.labcabrera.sample.archetype.domain.player.event.PlayerCreatedEvent;
import org.labcabrera.sample.archetype.domain.player.event.PlayerUpdatedEvent;

public interface PlayerEventBusPort {

    void publish(PlayerCreatedEvent event);

    void publish(PlayerUpdatedEvent event);
}
