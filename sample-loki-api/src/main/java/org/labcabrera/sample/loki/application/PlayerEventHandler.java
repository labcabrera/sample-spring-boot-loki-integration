package org.labcabrera.sample.loki.application;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.config.ProcessingGroup;
import org.labcabrera.sample.loki.domain.player.event.PlayerCreatedEvent;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@ProcessingGroup("player-events")
@RequiredArgsConstructor
@Slf4j
public class PlayerEventHandler {

    private final PlayerQueryHandler playerQueryHandler;

    @EventHandler
    public void on(PlayerCreatedEvent event) {
        log.info("Player created event received: playerId={}, name={}", 
                event.getPlayerId(), event.getName());
        
        // Aquí puedes agregar lógica para:
        // - Actualizar proyecciones/vistas de lectura
        // - Enviar notificaciones
        // - Integrar con otros sistemas
        // - Publicar a Kafka (cuando esté disponible)
        
        handlePlayerCreatedProjection(event);
        handlePlayerCreatedNotification(event);
    }
    
    private void handlePlayerCreatedProjection(PlayerCreatedEvent event) {
        log.debug("Updating player projection for playerId: {}", event.getPlayerId());
        // Actualizar la vista de lectura
        playerQueryHandler.updatePlayerView(event.getPlayerId(), event.getName());
    }
    
    private void handlePlayerCreatedNotification(PlayerCreatedEvent event) {
        log.debug("Sending notification for new player: {}", event.getName());
        // Aquí enviarías notificaciones, emails, etc.
    }
}