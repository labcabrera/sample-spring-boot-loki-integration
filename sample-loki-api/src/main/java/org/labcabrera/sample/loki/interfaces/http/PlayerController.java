package org.labcabrera.sample.loki.interfaces.http;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.queryhandling.QueryGateway;
import org.labcabrera.sample.loki.application.PlayerService;
import org.labcabrera.sample.loki.application.PlayerQueryHandler;
import org.labcabrera.sample.loki.domain.player.query.GetPlayerQuery;
import org.labcabrera.sample.loki.domain.player.query.GetPlayersByStatusQuery;
import org.labcabrera.sample.loki.domain.player.query.GetPlayersByEloRangeQuery;
import org.labcabrera.sample.loki.domain.player.query.PlayerView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
@Tag(name = "Players", description = "API para gestión de jugadores con arquitectura CQRS")
public class PlayerController {

    private final PlayerService playerService;
    private final QueryGateway queryGateway;
    private final PlayerQueryHandler playerQueryHandler;

    @PostMapping
    @Operation(
        summary = "Crear nuevo jugador",
        description = "Crea un nuevo jugador usando el patrón CQRS. Envía un comando que genera un evento."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jugador creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public Mono<ResponseEntity<PlayerCreatedResponse>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del jugador a crear",
                required = true,
                content = @Content(schema = @Schema(implementation = CreatePlayerRequest.class))
            )
            @RequestBody CreatePlayerRequest request) {
        String id = playerService.createPlayer(request.name(), request.email(), request.elo());
        return Mono.just(ResponseEntity.ok(new PlayerCreatedResponse(id, "Player created successfully")));
    }
    
    @GetMapping("/{playerId}")
    @Operation(
        summary = "Obtener jugador por ID",
        description = "Consulta un jugador específico por su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jugador encontrado"),
        @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public Mono<ResponseEntity<PlayerView>> getPlayer(
            @Parameter(description = "ID único del jugador", required = true)
            @PathVariable String playerId) {
        try {
            PlayerView player = queryGateway.query(new GetPlayerQuery(playerId), PlayerView.class).join();
            return Mono.just(ResponseEntity.ok(player));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.notFound().build());
        }
    }
    
    @GetMapping
    @Operation(
        summary = "Obtener todos los jugadores",
        description = "Retorna una lista completa de todos los jugadores registrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista de jugadores obtenida exitosamente")
    public Mono<ResponseEntity<Map<String, PlayerView>>> getAllPlayers() {
        Map<String, PlayerView> players = playerQueryHandler.getAllPlayers();
        return Mono.just(ResponseEntity.ok(players));
    }
    
    @GetMapping("/status/{status}")
    @Operation(
        summary = "Obtener jugadores por estado",
        description = "Filtra jugadores por su estado (ACTIVE, INACTIVE)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de jugadores filtrada por estado"),
        @ApiResponse(responseCode = "400", description = "Estado inválido")
    })
    public Mono<ResponseEntity<List<PlayerView>>> getPlayersByStatus(
            @Parameter(description = "Estado del jugador (ACTIVE, INACTIVE)", required = true)
            @PathVariable String status) {
        try {
            @SuppressWarnings("unchecked")
            List<PlayerView> players = queryGateway.query(new GetPlayersByStatusQuery(status), List.class).join();
            return Mono.just(ResponseEntity.ok(players));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
    }
    
    @GetMapping("/elo")
    @Operation(
        summary = "Obtener jugadores por rango de ELO",
        description = "Filtra jugadores dentro de un rango específico de puntuación ELO"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de jugadores en el rango de ELO especificado"),
        @ApiResponse(responseCode = "400", description = "Parámetros de rango inválidos")
    })
    public Mono<ResponseEntity<List<PlayerView>>> getPlayersByEloRange(
            @Parameter(description = "ELO mínimo", required = true)
            @RequestParam Integer minElo,
            @Parameter(description = "ELO máximo", required = true)
            @RequestParam Integer maxElo) {
        try {
            @SuppressWarnings("unchecked")
            List<PlayerView> players = queryGateway.query(
                new GetPlayersByEloRangeQuery(minElo, maxElo), List.class).join();
            return Mono.just(ResponseEntity.ok(players));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
    }

    @Schema(description = "Datos para crear un nuevo jugador")
    public static record CreatePlayerRequest(
            @Schema(description = "Nombre del jugador", example = "Magnus Carlsen", required = true)
            String name,
            @Schema(description = "Email único del jugador", example = "magnus@chess.com", required = true)
            String email,
            @Schema(description = "Puntuación ELO del jugador", example = "2800")
            Integer elo
    ) {}
    
    @Schema(description = "Respuesta tras crear un jugador")
    public static record PlayerCreatedResponse(
            @Schema(description = "ID único del jugador creado")
            String id,
            @Schema(description = "Mensaje de confirmación")
            String message
    ) {}
}
