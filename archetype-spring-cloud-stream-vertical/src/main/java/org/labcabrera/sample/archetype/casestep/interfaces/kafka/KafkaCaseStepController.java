package org.labcabrera.sample.archetype.casestep.interfaces.kafka;

import java.util.function.Consumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderCreatedEvent;
import org.labcabrera.sample.archetype.casestep.application.cqrs.commands.CreateInitialCaseStepCommand;
import org.labcabrera.sample.archetype.shared.application.CommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaCaseStepController {

    private final CommandBus commandBus;

    @Bean
    public Consumer<Message<CaseFolderCreatedEvent>> processInitialCaseStepCreation() {
        return message -> {
            log.debug("Received case folder created event: {}", message.getPayload().id());
            loadUserContext(message);
            try {
                commandBus.dispatch(new CreateInitialCaseStepCommand(message.getPayload().id()));
            }
            finally {
                SecurityContextHolder.clearContext();
            }
        };

    }

    private void loadUserContext(Message<?> message) {
        String username = message.getHeaders().get("x-username", String.class);
        String roles = message.getHeaders().get("x-roles", String.class);
        log.debug("Loading user context {} ({})", username, roles);
        if (username != null) {
            List<SimpleGrantedAuthority> authorities = (roles == null || roles.isBlank())
                ? List.of()
                : Arrays.stream(roles.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
}