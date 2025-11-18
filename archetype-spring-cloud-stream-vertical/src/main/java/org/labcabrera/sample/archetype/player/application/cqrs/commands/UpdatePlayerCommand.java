package org.labcabrera.sample.archetype.player.application.cqrs.commands;

import org.labcabrera.sample.archetype.player.domain.PlayerStatus;

public record UpdatePlayerCommand(
    String playerId,
    String name,
    PlayerStatus status) {
}
