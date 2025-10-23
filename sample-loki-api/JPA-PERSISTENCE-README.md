# Capa de Persistencia JPA

Esta aplicación ahora incluye una capa de persistencia completa basada en JPA (Java Persistence API) que complementa la arquitectura CQRS con Axon Framework.

## 🏗️ Arquitectura de Persistencia

### Componentes de la Capa de Persistencia

1. **Entidad JPA**
   - `PlayerEntity`: Entidad JPA con mapeo completo a tabla `players`
   - Incluye auditoría automática (created_at, updated_at)
   - Control de versión optimista con `@Version`
   - Enum para estados del jugador

2. **Repositorio**
   - `PlayerRepository`: Repositorio JPA con consultas personalizadas
   - Métodos de búsqueda por email, estado, rango de elo
   - Consultas JPQL para casos de uso específicos

3. **Mapper**
   - `PlayerMapper`: Conversión entre entidad JPA y DTOs
   - Mapeo bidireccional entre `PlayerEntity` y `PlayerView`

4. **Servicios**
   - `PlayerValidationService`: Validaciones de negocio
   - Verificación de unicidad de email
   - Validaciones pre-creación

## 📊 Esquema de Base de Datos

```sql
CREATE TABLE players (
    player_id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    elo INTEGER,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT DEFAULT 0
);
```

## 🔧 Configuración

### Base de Datos
- **H2 en memoria** para desarrollo y testing
- **PostgreSQL** configurado para producción
- **Console H2** disponible en `/h2-console`

### Propiedades JPA
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password: password
  
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: false
    properties:
      hibernate:
        format_sql: true
```

## 🚀 API Endpoints Extendidos

### Endpoints CRUD Básicos
```bash
# Crear jugador
POST /api/players
{
  "name": "Magnus Carlsen",
  "email": "magnus@chess.com",
  "elo": 2800
}

# Obtener jugador por ID
GET /api/players/{playerId}

# Obtener todos los jugadores
GET /api/players
```

### Nuevos Endpoints con JPA
```bash
# Obtener jugadores por estado
GET /api/players/status/ACTIVE
GET /api/players/status/INACTIVE

# Obtener jugadores por rango de ELO
GET /api/players/elo?minElo=1200&maxElo=1800
```

## 🔍 Queries Personalizadas

### Repositorio con JPQL
```java
@Query("SELECT p FROM PlayerEntity p WHERE p.elo >= :minElo AND p.elo <= :maxElo")
List<PlayerEntity> findByEloRange(@Param("minElo") Integer minElo, @Param("maxElo") Integer maxElo);

@Query("SELECT p FROM PlayerEntity p WHERE p.name ILIKE %:name%")
List<PlayerEntity> findByNameContainingIgnoreCase(@Param("name") String name);
```

## 🎯 Integración CQRS + JPA

### Command Side (Escritura)
- **Axon Aggregates** manejan los comandos
- **Event Sourcing** para el historial de cambios
- **Event Store** en memoria para eventos

### Query Side (Lectura)
- **JPA Repository** para consultas optimizadas
- **Event Handlers** actualizan las proyecciones JPA
- **Consistencia eventual** entre Command y Query sides

### Flujo de Datos
```
Command → Aggregate → Event → Event Handler → JPA Repository → Database
                        ↓
Query ← PlayerView ← Mapper ← PlayerEntity ← JPA Repository
```

## ✅ Validaciones de Negocio

### Validaciones Implementadas
- **Email único**: No se permiten emails duplicados
- **Campos obligatorios**: Validación de campos requeridos
- **Estados válidos**: Enum con estados permitidos

### Manejo de Errores
```java
// Email duplicado
throw new IllegalArgumentException("Email already exists: " + email);

// Player no encontrado
throw new RuntimeException("Player not found: " + playerId);
```

## 🔄 Sincronización CQRS

### Event Handler con JPA
```java
@EventHandler
public void on(PlayerCreatedEvent event) {
    // Actualizar proyección JPA
    playerQueryHandler.updatePlayerView(
        event.getPlayerId(), 
        event.getName(), 
        event.getEmail(), 
        event.getElo()
    );
}
```

## 🧪 Testing

### Datos de Prueba
- Script `data.sql` para datos iniciales (comentado)
- H2 Console para inspección: `http://localhost:8081/h2-console`
- Datos se recrean en cada reinicio (`ddl-auto: create-drop`)

### Ejemplo de Prueba
```bash
# Crear jugador
curl -X POST http://localhost:8081/api/players \
  -H "Content-Type: application/json" \
  -d '{"name": "Test Player", "email": "test@example.com", "elo": 1500}'

# Buscar por rango ELO
curl "http://localhost:8081/api/players/elo?minElo=1400&maxElo=1600"
```

## 📈 Beneficios de esta Arquitectura

1. **Separación de Responsabilidades**
   - Commands via Axon Event Sourcing
   - Queries via JPA optimizado

2. **Flexibilidad**
   - Queries complejas con JPA/JPQL
   - Escalabilidad independiente

3. **Consistencia**
   - Consistencia fuerte en aggregates
   - Consistencia eventual en proyecciones

4. **Auditabilidad**
   - Event Sourcing para historial completo
   - Timestamps automáticos en JPA

5. **Performance**
   - Event Store para writes rápidos
   - JPA con índices para reads optimizados

## 🔧 Configuración de Producción

Para usar PostgreSQL en producción:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/loki_db
    username: ${DB_USERNAME:loki_user}
    password: ${DB_PASSWORD:loki_pass}
  
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate
```

Esta arquitectura combina lo mejor de CQRS/Event Sourcing con la potencia y flexibilidad de JPA para crear un sistema robusto y escalable.