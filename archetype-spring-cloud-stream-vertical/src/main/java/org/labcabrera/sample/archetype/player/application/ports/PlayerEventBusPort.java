package org.labcabrera.sample.archetype.player.application.ports;

import org.labcabrera.sample.archetype.player.domain.event.PlayerCreatedEvent;
import org.labcabrera.sample.archetype.player.domain.event.PlayerUpdatedEvent;

public interface PlayerEventBusPort {

    void publish(PlayerCreatedEvent event);

    void publish(PlayerUpdatedEvent event);
}
