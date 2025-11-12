package org.labcabrera.sample.archetype.domain.player.aggregate;

import org.labcabrera.sample.archetype.application.cqrs.commands.CreatePlayerCommand;
import org.labcabrera.sample.archetype.domain.player.event.PlayerCreatedEvent;
import org.labcabrera.sample.archetype.domain.player.event.PlayerUpdatedEvent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class Player {

    private String id;
    private String name;
    private String email;
    private Integer elo;

}