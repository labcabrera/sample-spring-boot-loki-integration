package org.labcabrera.sample.archetype.application.cqrs.handlers;

import org.axonframework.queryhandling.QueryHandler;
import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayerByIdQuery;
import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayersByEloRangeQuery;
import org.labcabrera.sample.archetype.application.cqrs.queries.PlayerView;
import org.labcabrera.sample.archetype.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.infrastructure.persistence.entity.PlayerEntity;
import org.labcabrera.sample.archetype.infrastructure.persistence.mapper.PlayerMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerQueryHandler {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @QueryHandler
    public PlayerView handle(GetPlayerByIdQuery query) {
        log.debug("Handling query for player: {}", query.getPlayerId());
        PlayerEntity entity = playerRepository.findById(query.getPlayerId())
            .orElseThrow(() -> {
                log.warn("Player not found: {}", query.getPlayerId());
                return new RuntimeException("Player not found: " + query.getPlayerId());
            });
        return playerMapper.toPlayerView(entity);
    }

    @QueryHandler
    public List<PlayerView> handle(GetPlayersByEloRangeQuery query) {
        log.debug("Handling query for players by elo range: {} - {}", query.getMinElo(), query.getMaxElo());
        return getPlayersByEloRange(query.getMinElo(), query.getMaxElo());
    }

    public void updatePlayerView(String playerId, String name, String email, Integer elo) {
        PlayerEntity entity = playerRepository.findById(playerId)
            .orElse(playerMapper.toPlayerEntity(playerId, name, email, elo));
        if (entity.getPlayerId() == null) {
            entity = playerMapper.toPlayerEntity(playerId, name, email, elo);
        }
        else {
            playerMapper.updatePlayerEntity(entity, name, email, elo);
        }
        playerRepository.save(entity);
        log.debug("Player entity saved: {}", entity);
    }

    public Map<String, PlayerView> getAllPlayers() {
        List<PlayerEntity> entities = playerRepository.findAll();
        return entities.stream()
            .collect(Collectors.toMap(
                PlayerEntity::getPlayerId,
                playerMapper::toPlayerView));
    }

    public List<PlayerView> getPlayersByStatus(PlayerEntity.PlayerStatus status) {
        return playerRepository.findByStatus(status)
            .stream()
            .map(playerMapper::toPlayerView)
            .collect(Collectors.toList());
    }

    public List<PlayerView> getPlayersByEloRange(Integer minElo, Integer maxElo) {
        return playerRepository.findByEloRange(minElo, maxElo)
            .stream()
            .map(playerMapper::toPlayerView)
            .collect(Collectors.toList());
    }
}