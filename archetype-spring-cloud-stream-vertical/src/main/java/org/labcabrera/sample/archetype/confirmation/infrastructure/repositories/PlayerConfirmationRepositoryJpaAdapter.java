package org.labcabrera.sample.archetype.confirmation.infrastructure.repositories;

import org.labcabrera.sample.archetype.confirmation.application.ports.PlayerConfirmationRepository;
import org.labcabrera.sample.archetype.confirmation.domain.PlayerConfirmation;
import org.labcabrera.sample.archetype.confirmation.infrastructure.entities.PlayerConfirmationEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@SuppressWarnings("null")
public class PlayerConfirmationRepositoryJpaAdapter implements PlayerConfirmationRepository {

    private final PlayerConfirmationJpaRepository repository;
    private final ObjectMapper mapper;

    @Override
    public PlayerConfirmation save(PlayerConfirmation confirmation) {
        PlayerConfirmationEntity entity = mapper.convertValue(confirmation, PlayerConfirmationEntity.class);
        var saved = repository.save(entity);
        return mapper.convertValue(saved, PlayerConfirmation.class);
    }

    @Override
    public PlayerConfirmation findByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }

    @Override
    public void revokePrevious(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'revokePrevious'");
    }

}
