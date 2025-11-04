package org.labcabrera.sample.loki.application;

import org.axonframework.queryhandling.QueryHandler;
import org.labcabrera.sample.loki.domain.player.query.GetPlayerQuery;
import org.labcabrera.sample.loki.domain.player.query.GetPlayersByStatusQuery;
import org.labcabrera.sample.loki.domain.player.query.GetPlayersByEloRangeQuery;
import org.labcabrera.sample.loki.domain.player.query.PlayerView;
import org.labcabrera.sample.loki.infrastructure.persistence.entity.PlayerEntity;
import org.labcabrera.sample.loki.infrastructure.persistence.mapper.PlayerMapper;
import org.labcabrera.sample.loki.infrastructure.persistence.repository.PlayerRepository;
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
    public PlayerView handle(GetPlayerQuery query) {
        log.debug("Handling query for player: {}", query.getPlayerId());
        PlayerEntity entity = playerRepository.findById(query.getPlayerId())
            .orElseThrow(() -> {
                log.warn("Player not found: {}", query.getPlayerId());
                return new RuntimeException("Player not found: " + query.getPlayerId());
            });
        return playerMapper.toPlayerView(entity);
    }

    @QueryHandler
    public List<PlayerView> handle(GetPlayersByStatusQuery query) {
        log.debug("Handling query for players by status: {}", query.getStatus());
        PlayerEntity.PlayerStatus status = PlayerEntity.PlayerStatus.valueOf(query.getStatus().toUpperCase());
        return getPlayersByStatus(status);
    }

    @QueryHandler
    public List<PlayerView> handle(GetPlayersByEloRangeQuery query) {
        log.debug("Handling query for players by elo range: {} - {}", query.getMinElo(), query.getMaxElo());
        return getPlayersByEloRange(query.getMinElo(), query.getMaxElo());
    }

    // Método para actualizar la vista desde el event handler
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