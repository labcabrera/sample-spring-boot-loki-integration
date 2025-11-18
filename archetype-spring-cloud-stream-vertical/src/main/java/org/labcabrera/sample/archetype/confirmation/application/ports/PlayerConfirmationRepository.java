package org.labcabrera.sample.archetype.confirmation.application.ports;

import org.labcabrera.sample.archetype.confirmation.domain.PlayerConfirmation;

public interface PlayerConfirmationRepository {

    PlayerConfirmation save(PlayerConfirmation confirmation);

    PlayerConfirmation findByEmail(String email);

    void revokePrevious(String email);

}
