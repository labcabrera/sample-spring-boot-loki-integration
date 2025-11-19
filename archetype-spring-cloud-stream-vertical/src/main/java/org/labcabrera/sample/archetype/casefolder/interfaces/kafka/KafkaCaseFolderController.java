package org.labcabrera.sample.archetype.casefolder.interfaces.kafka;

import java.util.function.Consumer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.messaging.Message;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.CreateCaseFolderCommand;
import org.labcabrera.sample.archetype.shared.application.CommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaCaseFolderController {

    private final CommandBus commandBus;

    @Bean
    public Consumer<Message<CreateCaseFolderCommand>> processCaseFolderCreation() {
        return message -> {
            String username = message.getHeaders().get("username", String.class);
            String roles = message.getHeaders().get("roles", String.class);

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

            try {
                commandBus.dispatch(message.getPayload());
            }
            finally {
                SecurityContextHolder.clearContext();
            }
        };
    }
}
