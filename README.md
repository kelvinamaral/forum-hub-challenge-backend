# Fórum Hub Challenge Back End

### Descrição do Projeto

O **Fórum Hub** é uma API RESTful projetada para simular o back-end de uma plataforma de fórum de discussões. Desenvolvido em Java com o framework **Spring Boot**, o projeto foca na implementação das operações completas de CRUD (Create, Read, Update, Delete) para gerenciar tópicos, usuários e respostas, com atenção especial às melhores práticas de desenvolvimento, regras de negócio e segurança.

O principal objetivo deste projeto é consolidar conhecimentos em:

* **API RESTful:** Implementação de rotas e verbos HTTP seguindo o modelo REST.
* **Persistência de Dados:** Utilização de um banco de dados relacional (MySQL) para persistir informações.
* **Segurança:** Implementação de um serviço de autenticação e autorização via JWT (JSON Web Token) para restringir o acesso a rotas protegidas.
* **Validações:** Aplicação de regras de negócio para garantir a integridade dos dados.

### Funcionalidades (Endpoints)

A API oferece os seguintes endpoints para interação:

* `POST /login`: Autenticação de usuário e geração de token JWT.
* `POST /topicos`: Cria um novo tópico.
* `GET /topicos`: Lista todos os tópicos existentes, com suporte para paginação.
* `GET /topicos/{id}`: Exibe um tópico específico por ID.
* `PUT /topicos/{id}`: Atualiza um tópico existente.
* `DELETE /topicos/{id}`: Deleta um tópico.
* `POST /usuarios`: Cria um novo usuário.
* `PUT /usuarios/{id}`: Atualiza as informações de um usuário.
* `DELETE /usuarios/{id}`: Desativa um usuário.
* `POST /respostas`: Cria uma nova resposta para um tópico.

### Tecnologias Utilizadas

* **Java 22:** Linguagem de programação.
* **Spring Boot:** Framework para desenvolvimento de APIs.
* **Spring Security:** Gerenciamento de autenticação e autorização.
* **JWT (JSON Web Token):** Serviço de autenticação e autorização.
* **Spring Data JPA:** Persistência e consultas ao banco de dados.
* **Flyway:** Gerenciamento de migrações do banco de dados.
* **MySQL:** Banco de dados relacional.
* **Insomnia:** Cliente REST para testar os endpoints da API.

### Como Rodar o Projeto

1.  **Pré-requisitos:**
    * JDK 22 ou superior.
    * MySQL 8.0 ou superior instalado e rodando.

2.  **Configuração do Banco de Dados:**
    * Crie um banco de dados chamado `forum_hub_alura` no seu MySQL.
    * Configure as credenciais do banco de dados no arquivo `application.properties` ou `application.yml`.
    * As migrações do Flyway serão executadas automaticamente ao iniciar a aplicação, criando as tabelas necessárias.

3.  **Execução:**
    * Clone este repositório.
    * Abra o projeto na sua IDE (IntelliJ, VS Code).
    * Execute a classe principal `DesafioForumHubApplication`.
    * A API estará disponível em `http://localhost:8080`.

### Como Testar a API

Use o Insomnia ou Postman para testar os endpoints.

1.  **Login:**
    * Envie um `POST` para `http://localhost:8080/login` com as credenciais de um usuário cadastrado.
    * A API retornará um token JWT.

2.  **Acesso a Rotas Protegidas:**
    * Para as demais rotas, adicione o token JWT no cabeçalho da requisição:
    * `Authorization: Bearer <seu_token_jwt>`

3.  **Exemplo de JSON para Criar Tópico:**
    ```json
    {
        "titulo": "Exemplo de título",
        "mensagem": "Exemplo de mensagem para o tópico.",
        "cursoId": 1
    }
    ```
### Obrigado!