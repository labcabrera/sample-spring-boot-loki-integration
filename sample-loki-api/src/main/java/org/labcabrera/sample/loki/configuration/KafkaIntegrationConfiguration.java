package org.labcabrera.sample.loki.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuración para integración con Kafka.
 * Esta configuración se activa cuando el perfil 'kafka' está habilitado.
 * 
 * Para habilitar Kafka, ejecutar con: --spring.profiles.active=kafka
 * También es necesario tener Kafka corriendo en localhost:9092
 */
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