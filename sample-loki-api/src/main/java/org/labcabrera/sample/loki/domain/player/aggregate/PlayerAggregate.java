package org.labcabrera.sample.loki.domain.player.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.labcabrera.sample.loki.domain.player.command.CreatePlayerCommand;
import org.labcabrera.sample.loki.domain.player.event.PlayerCreatedEvent;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
@Getter
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
        this.playerId = evt.getPlayerId();
        this.name = evt.getName();
        this.email = evt.getEmail();
        this.elo = evt.getElo();
    }
}
