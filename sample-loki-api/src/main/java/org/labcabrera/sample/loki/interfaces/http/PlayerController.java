package org.labcabrera.sample.loki.interfaces.http;

import org.labcabrera.sample.loki.application.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public Mono<ResponseEntity<String>> create(@RequestBody PlayerDto dto) {
    String id = playerService.createPlayer(dto.name());
        return Mono.just(ResponseEntity.ok(id));
    }

    public static record PlayerDto(String name) {}
}
