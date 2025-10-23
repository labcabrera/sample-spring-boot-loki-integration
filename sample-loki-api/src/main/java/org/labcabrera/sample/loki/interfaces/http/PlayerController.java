package org.labcabrera.sample.loki.interfaces.http;

import org.axonframework.queryhandling.QueryGateway;
import org.labcabrera.sample.loki.application.PlayerService;
import org.labcabrera.sample.loki.application.PlayerQueryHandler;
import org.labcabrera.sample.loki.domain.player.query.GetPlayerQuery;
import org.labcabrera.sample.loki.domain.player.query.PlayerView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final QueryGateway queryGateway;
    private final PlayerQueryHandler playerQueryHandler;

    @PostMapping
    public Mono<ResponseEntity<PlayerCreatedResponse>> create(@RequestBody CreatePlayerRequest request) {
        String id = playerService.createPlayer(request.name(), request.email(), request.elo());
        return Mono.just(ResponseEntity.ok(new PlayerCreatedResponse(id, "Player created successfully")));
    }
    
    @GetMapping("/{playerId}")
    public Mono<ResponseEntity<PlayerView>> getPlayer(@PathVariable String playerId) {
        try {
            PlayerView player = queryGateway.query(new GetPlayerQuery(playerId), PlayerView.class).join();
            return Mono.just(ResponseEntity.ok(player));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.notFound().build());
        }
    }
    
    @GetMapping
    public Mono<ResponseEntity<Map<String, PlayerView>>> getAllPlayers() {
        Map<String, PlayerView> players = playerQueryHandler.getAllPlayers();
        return Mono.just(ResponseEntity.ok(players));
    }

    public static record CreatePlayerRequest(String name, String email, Integer elo) {}
    public static record PlayerCreatedResponse(String id, String message) {}
}
