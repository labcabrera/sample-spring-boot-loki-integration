# Configuración CQRS con Axon Framework

Esta aplicación implementa el patrón CQRS (Command Query Responsibility Segregation) usando Axon Framework.

## Arquitectura CQRS

### Componentes Principales

1. **Command Side (Escritura)**
   - `PlayerAggregate`: Agregado que maneja comandos y genera eventos
   - `CreatePlayerCommand`: Comando para crear un jugador
   - `PlayerService`: Servicio de aplicación que envía comandos

2. **Event Side**
   - `PlayerCreatedEvent`: Evento generado cuando se crea un jugador
   - `PlayerEventHandler`: Maneja eventos y actualiza proyecciones

3. **Query Side (Lectura)**
   - `GetPlayerQuery`: Query para obtener información de un jugador
   - `PlayerView`: DTO de lectura con la información del jugador
   - `PlayerQueryHandler`: Maneja queries y mantiene proyecciones

4. **HTTP Interface**
   - `PlayerController`: API REST que expone comandos y queries

### Configuración

La aplicación está configurada en `AxonConfiguration.java` con:
- Serialización JSON usando Jackson
- Procesamiento asíncrono de eventos
- Token store en memoria
- Procesador específico para eventos de player

## API Endpoints

### Crear Jugador (Command)
```bash
POST /api/players
Content-Type: application/json

{
  "name": "Juan Pérez"
}
```

### Obtener Jugador (Query)
```bash
GET /api/players/{playerId}
```

### Obtener Todos los Jugadores (Query)
```bash
GET /api/players
```

## Configuración de Logging

La aplicación incluye configuración para logging estructurado y no estructurado:

- **Logging estándar (default)**: Formato legible para desarrollo
- **Logging JSON**: Activar con `--spring.profiles.active=json`

## Integración con Kafka (Futuro)

La aplicación está preparada para integración con Kafka:

1. **Configuración lista**: `KafkaIntegrationConfiguration` (perfil 'kafka')
2. **Dependencias**: Añadir spring-kafka al build.gradle
3. **Event Publishing**: Extensión del PlayerEventHandler para publicar a Kafka

### Para habilitar Kafka:

1. Añadir dependencias:
```gradle
implementation 'org.springframework.kafka:spring-kafka'
```

2. Configurar properties:
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```

3. Activar perfil kafka:
```bash
--spring.profiles.active=kafka
```

## Beneficios de esta Arquitectura

1. **Separación de Responsabilidades**: Comandos y queries están claramente separados
2. **Escalabilidad**: El lado de lectura puede escalar independientemente
3. **Consistencia Eventual**: Los eventos garantizan sincronización entre agregados
4. **Auditabilidad**: Todos los cambios quedan registrados como eventos
5. **Flexibilidad**: Fácil agregar nuevas proyecciones o event handlers

## Event Sourcing

Axon Framework provee Event Sourcing automáticamente:
- Los agregados se reconstruyen desde eventos
- Historial completo de cambios
- Capacidad de replay de eventos
- Snapshots automáticos para performance

## Monitoring

La aplicación expone métricas de Axon en:
- `/actuator/axon`: Información de estado de Axon
- `/actuator/metrics`: Métricas de Micrometer
- `/actuator/health`: Health checks

## Testing

Para probar la funcionalidad CQRS:

1. Crear un jugador:
```bash
curl -X POST http://localhost:8081/api/players \
  -H "Content-Type: application/json" \
  -d '{"name": "Test Player"}'
```

2. Obtener el jugador:
```bash
curl http://localhost:8081/api/players/{playerId}
```

3. Ver todos los jugadores:
```bash
curl http://localhost:8081/api/players
```