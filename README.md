# 🚀 User Management Service — Kotlin + Spring Boot + Kafka

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.23-7F52FF?logo=kotlin)](https://kotlinlang.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F?logo=spring-boot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql)](https://www.postgresql.org/)
[![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-producer-231F20?logo=apachekafka)](https://kafka.apache.org/)
[![Swagger](https://img.shields.io/badge/Documentation-Swagger-85EA2D?logo=swagger)](http://localhost:8080/swagger-ui/index.html)

API REST de gerenciamento de usuários em **Kotlin** com **Spring Boot 3**. É o serviço de origem do meu ecossistema de microsserviços: cada operação gera um registro de auditoria, e cada usuário criado é publicado como evento no **Apache Kafka**.

---

## 🧩 Onde este serviço se encaixa

```
                    HTTP  POST /audit/log  (CREATE, UPDATE, DELETE)
user-management  ─────────────────────────────────────────────►  audit-event-consumer (Java)
  (Kotlin)
                    Kafka  tópico audit-log-topic  (usuário criado)
                 ─────────────────────────────────────────────►  consumidor em desenvolvimento

infrastructure-health-monitor (Go) verifica a cada 10 s se os bancos estão online
```

- [audit-event-consumer](https://github.com/Vinicius-Infra/audit-event-consumer) — auditoria em Java/Spring Boot
- [infrastructure-health-monitor](https://github.com/Vinicius-Infra/infrastructure-health-monitor) — monitor de saúde em Go

---

## 🛠️ Tecnologias

- **Linguagem:** Kotlin 1.9.23 (JVM, JDK 17+)
- **Framework:** Spring Boot 3.2.5 (Web, Data JPA, Validation, Actuator)
- **Persistência:** Spring Data JPA + Hibernate
- **Banco de dados:** PostgreSQL 16
- **Mensageria:** Spring Kafka (produtor)
- **Documentação:** SpringDoc OpenAPI (Swagger)
- **Testes:** JUnit 5, Mockito e Spring Boot Test (MockMvc)
- **Build:** Gradle (Kotlin DSL)
- **Infraestrutura local:** Docker Compose (PostgreSQL + Kafka em modo KRaft)

---

## 🏗️ Arquitetura

Estrutura em camadas do Spring:

- `model` — entidade JPA `User` com validação (nome obrigatório, e-mail válido)
- `repository` — acesso ao PostgreSQL
- `service` — regras de negócio e envio da auditoria (HTTP)
- `service/KafkaProducerService` — publica eventos no tópico `audit-log-topic`
- `controller` — endpoints REST documentados no Swagger
- `exception` — `GlobalExceptionHandler` (400 para validação, 404 para usuário inexistente)
- `config` — configuração do OpenAPI

### Eventos publicados

| Quando | Destino | Conteúdo |
| --- | --- | --- |
| Usuário criado, atualizado ou removido | `POST /audit/log` (HTTP) | `userId`, `userName`, `operation` |
| Usuário criado | Kafka, tópico `audit-log-topic` | `Usuário criado: <nome> \| ID: <id>` |

---

## 🚀 Como rodar

### Pré-requisitos
- JDK 17+
- Docker e Docker Compose

### 1. Subir PostgreSQL e Kafka
```bash
docker-compose up -d
```
- PostgreSQL: porta `5434`, banco `demo_db` (usuário `demo`, senha `demo123`)
- Kafka: `localhost:9092`, com criação automática de tópicos

### 2. Executar a aplicação
```bash
./gradlew bootRun
```
A API sobe em `http://localhost:8080`.

Para o registro de auditoria via HTTP, suba também o [audit-event-consumer](https://github.com/Vinicius-Infra/audit-event-consumer) na porta `8083`. Se ele estiver fora do ar, a API continua funcionando e apenas registra a falha no log.

---

## 📖 Documentação da API

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Health check: http://localhost:8080/actuator/health

### Endpoints

| Método | Endpoint | Descrição |
| --- | --- | --- |
| GET | `/users` | Lista todos os usuários |
| GET | `/users/{id}` | Busca um usuário por ID (404 se não existir) |
| POST | `/users` | Cria um usuário (valida nome e e-mail) e publica o evento no Kafka |
| PUT | `/users/{id}` | Atualiza um usuário existente |
| DELETE | `/users/{id}` | Remove um usuário |

---

## 🧪 Testes

```bash
./gradlew test
```

- **`UserServiceTest`** — testes unitários da camada de serviço com Mockito (listar, buscar, erro de usuário inexistente, salvar e remover).
- **`UserControllerIntegrationTest`** — testes de integração dos endpoints com `@SpringBootTest` e MockMvc (POST, GET, GET por ID, PUT e DELETE). Precisa do PostgreSQL e do Kafka rodando (`docker-compose up -d`).

---

## 🔭 Próximos passos

- Publicar todas as operações (CREATE, UPDATE, DELETE) no Kafka em formato JSON, substituindo a chamada HTTP.
- Implementar o consumidor no audit-event-consumer com retry e Dead Letter Topic.
- Usar Testcontainers nos testes de integração, para não depender de serviços rodando localmente.

---

**Autor:** Marcos Vinícius da Silva Barreto
**LinkedIn:** [vinicius-barreto-tec](https://www.linkedin.com/in/vinicius-barreto-tec/)
**GitHub:** [Vinicius-Infra](https://github.com/Vinicius-Infra)

Developed with ❤️ and Kotlin.
