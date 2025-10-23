package org.labcabrera.sample.loki.application;

import org.axonframework.queryhandling.QueryHandler;
import org.labcabrera.sample.loki.domain.player.query.GetPlayerQuery;
import org.labcabrera.sample.loki.domain.player.query.PlayerView;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class PlayerQueryHandler {

    // Simulación de una base de datos de lectura
    // En un entorno real, esto sería una conexión a base de datos, Redis, etc.
    private final Map<String, PlayerView> playerViews = new ConcurrentHashMap<>();

    @QueryHandler
    public PlayerView handle(GetPlayerQuery query) {
        log.debug("Handling query for player: {}", query.getPlayerId());
        
        PlayerView playerView = playerViews.get(query.getPlayerId());
        if (playerView == null) {
            log.warn("Player not found: {}", query.getPlayerId());
            throw new RuntimeException("Player not found: " + query.getPlayerId());
        }
        
        return playerView;
    }
    
    // Método para actualizar la vista desde el event handler
    public void updatePlayerView(String playerId, String name, String email, Integer elo) {
        PlayerView playerView = new PlayerView(playerId, name, email, elo, "ACTIVE");
        playerViews.put(playerId, playerView);
        log.debug("Player view updated: {}", playerView);
    }
    
    // Método para obtener todos los players (para testing)
    public Map<String, PlayerView> getAllPlayers() {
        return Map.copyOf(playerViews);
    }
}