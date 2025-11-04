package org.labcabrera.sample.archetype.domain.player.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.labcabrera.sample.archetype.domain.player.command.CreatePlayerCommand;
import org.labcabrera.sample.archetype.domain.player.event.PlayerCreatedEvent;
import org.labcabrera.sample.archetype.domain.player.event.PlayerUpdatedEvent;
import org.axonframework.modelling.command.AggregateLifecycle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aggregate
@NoArgsConstructor
@Getter
@Slf4j
public class PlayerAggregate {

    @AggregateIdentifier
    private String playerId;
    private String name;
    private String email;
    private Integer elo;

    @CommandHandler
    public PlayerAggregate(CreatePlayerCommand cmd) {
        AggregateLifecycle.apply(new PlayerCreatedEvent(cmd.getPlayerId(), cmd.getName(), cmd.getEmail(), cmd.getElo()));
    }

    @EventSourcingHandler
    public void on(PlayerCreatedEvent evt) {
        log.debug("Applying PlayerCreatedEvent for playerId: {}", evt.getPlayerId());
        this.playerId = evt.getPlayerId();
        this.name = evt.getName();
        this.email = evt.getEmail();
        this.elo = evt.getElo();
    }

    @EventSourcingHandler
    public void on(PlayerUpdatedEvent evt) {
        log.debug("Applying PlayerUpdatedEvent for playerId: {}", evt.getPlayerId());
        this.name = evt.getName();
        this.email = evt.getEmail();
    }
}