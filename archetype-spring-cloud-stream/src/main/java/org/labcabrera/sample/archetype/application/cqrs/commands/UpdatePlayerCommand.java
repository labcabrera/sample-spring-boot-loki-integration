package org.labcabrera.sample.archetype.application.cqrs.commands;

import org.labcabrera.sample.archetype.domain.player.PlayerStatus;

public record UpdatePlayerCommand(
    String playerId,
    String name,
    PlayerStatus status) {
}
