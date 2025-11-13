package org.labcabrera.sample.archetype.player.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.sample.archetype.player.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.player.domain.Player;
import org.labcabrera.sample.archetype.player.infrastructure.persistence.jpa.entities.PlayerEntity;
import org.labcabrera.sample.archetype.shared.domain.exceptions.BadRequestException;
import org.labcabrera.sample.archetype.shared.domain.exceptions.NotModifiedException;
import org.labcabrera.sample.archetype.shared.infrastructure.persistence.rsql.CustomRsqlVisitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("null")
public class PlayerRepositoryJpaAdapter implements PlayerRepository {

    private final PlayerJpaRepository jpaRepository;
    private final ObjectMapper objectMapper;
    private final RSQLParser rsqlParser;

    @Override
    public Optional<Player> findById(String playerId) {
        return jpaRepository.findById(playerId)
            .map(entity -> objectMapper.convertValue(entity, Player.class));
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
            .map(entity -> objectMapper.convertValue(entity, Player.class));
    }

    @Override
    public Page<Player> findByRsql(String rsql, Pageable pageable) {
        if (StringUtils.isBlank(rsql)) {
            var page = jpaRepository.findAll(pageable);
            return page.map(entity -> objectMapper.convertValue(entity, Player.class));
        }
        try {
            Node rootNode = rsqlParser.parse(rsql);
            Specification<PlayerEntity> spec = rootNode.accept(new CustomRsqlVisitor<PlayerEntity>());
            var page = jpaRepository.findAll(spec, pageable);
            return page.map(entity -> objectMapper.convertValue(entity, Player.class));
        }
        catch (Exception ex) {
            log.error("Error parsing RSQL query: {}", rsql, ex);
            throw new BadRequestException("Error parsing RSQL query " + rsql, ex);
        }
    }

    @Override
    public Player save(Player player) {
        var entity = objectMapper.convertValue(player, PlayerEntity.class);
        var savedEntity = jpaRepository.save(entity);
        return objectMapper.convertValue(savedEntity, Player.class);
    }

    @Override
    public Player update(Player player) {
        var current = jpaRepository.findById(player.getId())
            .orElseThrow(() -> new BadRequestException("Player not found with id: " + player.getId()));
        boolean modified = false;
        if (!current.getName().equals(player.getName())) {
            current.setName(player.getName());
            modified = true;
        }
        if (!modified) {
            throw new NotModifiedException("No changes detected for player with id: " + player.getId());
        }
        var savedEntity = jpaRepository.save(current);
        return objectMapper.convertValue(savedEntity, Player.class);
    }

    @Override
    public void deleteById(String playerId) {
        jpaRepository.deleteById(playerId);
    }

}
