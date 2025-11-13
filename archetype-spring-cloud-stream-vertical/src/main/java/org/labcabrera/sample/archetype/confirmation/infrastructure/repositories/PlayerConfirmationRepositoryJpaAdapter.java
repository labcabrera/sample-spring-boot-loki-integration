package org.labcabrera.sample.archetype.confirmation.infrastructure.repositories;

import org.labcabrera.sample.archetype.confirmation.application.ports.PlayerConfirmationRepository;
import org.labcabrera.sample.archetype.confirmation.domain.PlayerConfirmation;

public class PlayerConfirmationRepositoryJpaAdapter implements PlayerConfirmationRepository {

    @Override
    public PlayerConfirmation save(PlayerConfirmation confirmation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
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
