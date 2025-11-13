package org.labcabrera.sample.archetype.player.application.ports;

import org.labcabrera.sample.archetype.player.domain.events.PlayerCreatedEvent;
import org.labcabrera.sample.archetype.player.domain.events.PlayerUpdatedEvent;

public interface PlayerEventBusPort {

    void publish(PlayerCreatedEvent event);

    void publish(PlayerUpdatedEvent event);
}
