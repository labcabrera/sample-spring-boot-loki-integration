package org.labcabrera.sample.archetype.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.sample.archetype.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.domain.player.aggregate.Player;
import org.labcabrera.sample.archetype.infrastructure.persistence.entity.PlayerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepository {

    private final PlayerJpaRepository jpaRepository;
    private final ObjectMapper objectMapper;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByRsql'");
    }

    @Override
    public List<Player> findByEloRange(Integer minElo, Integer maxElo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEloRange'");
    }

    @Override
    public Player save(Player player) {
        var entity = objectMapper.convertValue(player, PlayerEntity.class);
        var savedEntity = jpaRepository.save(entity);
        return objectMapper.convertValue(savedEntity, Player.class);
    }

    @Override
    public Player update(Player player) {
        var entity = objectMapper.convertValue(player, PlayerEntity.class);
        var savedEntity = jpaRepository.save(entity);
        return objectMapper.convertValue(savedEntity, Player.class);
    }

    @Override
    public List<Player> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
