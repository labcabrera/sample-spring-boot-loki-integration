package org.labcabrera.sample.archetype.infrastructure.persistence.mapper;

import org.labcabrera.sample.archetype.domain.player.query.PlayerView;
import org.labcabrera.sample.archetype.infrastructure.persistence.entity.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerView toPlayerView(PlayerEntity entity) {
        return new PlayerView(
            entity.getPlayerId(),
            entity.getName(),
            entity.getEmail(),
            entity.getElo(),
            entity.getStatus().name());
    }

    public PlayerEntity toPlayerEntity(String playerId, String name, String email, Integer elo) {
        PlayerEntity entity = new PlayerEntity();
        entity.setPlayerId(playerId);
        entity.setName(name);
        entity.setEmail(email);
        entity.setElo(elo);
        entity.setStatus(PlayerEntity.PlayerStatus.ACTIVE);
        return entity;
    }

    public void updatePlayerEntity(PlayerEntity entity, String name, String email, Integer elo) {
        if (name != null) {
            entity.setName(name);
        }
        if (email != null) {
            entity.setEmail(email);
        }
        if (elo != null) {
            entity.setElo(elo);
        }
    }
}