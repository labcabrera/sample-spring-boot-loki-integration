package org.labcabrera.sample.archetype.confirmation.application.ports;

public interface EmailSenderPort {

    void sendEmail(String to, String confirmationCode);

}
