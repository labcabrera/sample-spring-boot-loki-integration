package org.labcabrera.sample.archetype.confirmation.application.cqrs.handlers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.labcabrera.sample.archetype.confirmation.application.cqrs.commands.PlayerConfirmationCommand;
import org.labcabrera.sample.archetype.confirmation.application.ports.EmailSenderPort;
import org.labcabrera.sample.archetype.confirmation.application.ports.PlayerConfirmationRepository;
import org.labcabrera.sample.archetype.confirmation.domain.PlayerConfirmation;
import org.labcabrera.sample.archetype.player.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.player.domain.PlayerStatus;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.labcabrera.sample.archetype.shared.domain.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerConfirmationCommandHandler implements CommandHandler<PlayerConfirmationCommand, PlayerConfirmation> {

    private final PlayerRepository playerRepository;
    private final PlayerConfirmationRepository playerConfirmationRepository;
    private final EmailSenderPort emailSenderPort;

    @Override
    public PlayerConfirmation handle(PlayerConfirmationCommand command) {
        log.info("Creating player email confirmation << {}", command.email());
        var player = playerRepository.findByEmail(command.email())
            .orElseThrow(() -> new BadRequestException("Player not found with email: " + command.email()));
        if (player.getStatus() != PlayerStatus.UNCONFIRMED_EMAIL) {
            throw new BadRequestException("Invalid player status for confirmation");
        }
        String confirmationCode = RandomStringUtils.secure().nextNumeric(6);
        String confirmationToken = UUID.randomUUID().toString();
        PlayerConfirmation confirmation = new PlayerConfirmation(
            UUID.randomUUID().toString(),
            command.email(),
            confirmationCode,
            confirmationToken,
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(24),
            null,
            null);
        playerConfirmationRepository.revokePrevious(confirmationToken);
        playerConfirmationRepository.save(confirmation);
        emailSenderPort.sendEmail(command.email(), confirmationCode);
        return confirmation;
    }

}