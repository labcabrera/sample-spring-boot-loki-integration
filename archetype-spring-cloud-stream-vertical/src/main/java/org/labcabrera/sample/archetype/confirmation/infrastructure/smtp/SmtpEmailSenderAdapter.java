package org.labcabrera.sample.archetype.confirmation.infrastructure.smtp;

import org.labcabrera.sample.archetype.confirmation.application.ports.EmailSenderPort;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SmtpEmailSenderAdapter implements EmailSenderPort {

    @Override
    public void sendEmail(String to, String confirmationCode) {
        log.info("Sending confirmation mail to {}", to);
    }

}
