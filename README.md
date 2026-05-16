# 🚀 Kotlin API Demo - Microservices

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.23-7F52FF?logo=kotlin)](https://kotlinlang.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F?logo=spring-boot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?logo=postgresql)](https://www.postgresql.org/)
[![Swagger](https://img.shields.io/badge/Documentation-Swagger-85EA2D?logo=swagger)](http://localhost:8080/swagger-ui/index.html)

Esta é uma API REST robusta desenvolvida em **Kotlin** com **Spring Boot 3**, focada em boas práticas de desenvolvimento, validação de dados e documentação automatizada. O projeto serve como um boilerplate para microsserviços modernos.

---

## 🛠️ Tecnologias e Ferramentas

- **Linguagem:** [Kotlin](https://kotlinlang.org/) (JVM)
- **Framework:** [Spring Boot 3.2.5](https://spring.io/projects/spring-boot)
- **Persistência:** [Spring Data JPA](https://spring.io/projects/spring-data-jpa) & [Hibernate](https://hibernate.org/)
- **Banco de Dados:** [PostgreSQL](https://www.postgresql.org/)
- **Documentação:** [SpringDoc OpenAPI (Swagger)](https://springdoc.org/)
- **Validação:** [Bean Validation (Jakarta)](https://beanvalidation.org/)
- **Build Tool:** [Gradle (Kotlin DSL)](https://gradle.org/)

---

## 🏗️ Arquitetura do Projeto

O projeto segue a estrutura padrão de camadas do Spring, garantindo separação de responsabilidades:

- `model`: Entidades JPA e regras de validação.
- `repository`: Interfaces de comunicação com o banco de dados.
- `service`: Camada de lógica de negócio e tratamento de exceções.
- `controller`: Endpoints REST e documentação Swagger.
- `exception`: Manipulador global de erros (Global Exception Handler).
- `config`: Configurações de infraestrutura e OpenAPI.

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
- JDK 17+
- Docker & Docker Compose (Recomendado para o Banco de Dados)

### 1. Subir o Banco de Dados (PostgreSQL)
Certifique-se de que o Postgres está rodando na porta `5434` conforme configurado no `application.yaml`:
```bash
docker run --name demo-db -e POSTGRES_USER=demo -e POSTGRES_PASSWORD=demo123 -e POSTGRES_DB=demo_db -p 5434:5432 -d postgres


2. Compilar e Executar


# Limpar e compilar
./gradlew clean compileKotlin

# Executar a aplicação
./gradlew bootRun

A aplicação estará disponível em http://localhost:8080.


📖 Documentação da APICom a aplicação rodando, você pode acessar a interface interativa do Swagger para testar os endpoints:

🔗 Swagger UI: http://localhost:8080/swagger-ui/index.html
📄 OpenAPI JSON: http://localhost:8080/v3/api-docs

🧪 Endpoints Principais

Método        Endpoint                   Descrição

GET           /users                     Lista todos os usuários.GET/users/{id}Busca um usuário por ID (retorna 404 se não existir).
POST          /users                     Cria um novo usuário (Valida nome e e-mail).
PUT           /users/{id}                Atualiza dados de um usuário existente.DELETE/users/{id}Remove um usuário do sistema.

🛡️ Tratamento de Erros e Validação

A API possui um Global Exception Handler que captura:

MethodArgumentNotValidException: Retorna erro 400 com detalhes dos campos inválidos.

UserNotFoundException: Exceção personalizada que retorna erro 404.

Autor: Marcos Vinícius da Silva Barreto 
LinkedIn: https://www.linkedin.com/in/vinicius-barreto-devops/  
GitHub: https://github.com/Vinicius-Infra

Developed with ❤️ and Kotlin.

