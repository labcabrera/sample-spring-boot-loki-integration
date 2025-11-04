package org.labcabrera.sample.archetype.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Profile("kafka")
@Slf4j
public class KafkaIntegrationConfiguration {

    public KafkaIntegrationConfiguration() {
        log.info("Kafka integration configuration loaded");
        log.info("To fully enable Kafka integration:");
        log.info("1. Add spring-kafka dependency to build.gradle");
        log.info("2. Add Kafka configuration properties to application.yaml");
        log.info("3. Implement KafkaEventPublisher in PlayerEventHandler");
        log.info("4. Start Kafka server on localhost:9092");
    }

    // Aquí irán los beans de configuración de Kafka cuando esté disponible
    // @Bean
    // public KafkaTemplate<String, Object> kafkaTemplate() { ... }

    // @Bean  
    // public NewTopic playerEventsTopic() { ... }
}