package org.labcabrera.sample.loki.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.inmemory.InMemoryTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfiguration {

    @Bean
    @Primary
    public Serializer axonJsonSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return JacksonSerializer.builder()
                .objectMapper(objectMapper)
                .build();
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer, TokenStore tokenStore) {
        // Configure async processing for player events
        configurer.registerPooledStreamingEventProcessor(
            "player-events"
        );
        
        // Configure token store
        configurer.registerTokenStore("player-events", conf -> tokenStore);
        
        // Configure batch size and thread pool for async processing
        configurer.registerPooledStreamingEventProcessorConfiguration(
            "player-events", 
            (config, builder) -> builder
                .batchSize(100)
                .maxClaimedSegments(4)
        );
    }
}