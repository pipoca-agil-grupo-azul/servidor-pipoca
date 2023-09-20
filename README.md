# Back-end Podcast Pipoca Ágil

Repositório do código fonte do projeto Pipoca Ágil da **Equipe Azul.** O projeto consiste em criar um site totalmente novo para o **Podcast Pipoca Ágil** conduzido pelo Scrum Master **Ibson Cabral.** utilizando as tecnologias decididas pelo time ágil. Este documento descreve detalhadamente o funcionamento e estrutura do projeto em Spring Boot que inclui configurações de segurança, controladores REST, modelos de dados, persistência em banco de dados PostgreSQL, integração com APIs externas, tratamento de exceções, entre outros componentes. O projeto é uma aplicação de servidor com recursos de autenticação, autorização, envio de e-mails, integração com a API do YouTube e gerenciamento de usuários.

## Tecnologias utilizadas

Para o desenvolvimento do projeto, foram utilizado as Tecnologias: Spring boot, Maven, Postgres, Docker e git.

## Documentação Swagger

Este projeto utiliza a swagger-ui para gerar a documentação swagger, acesse aqui:
- https://backend-pipoca.onrender.com/swagger-ui/index.html

## Estrutura de Pacotes

O segue uma estrutura de pacotes comum para aplicativos Spring Boot:

- `com.servidorpipoca.pipocaagil`: Pacote raiz do projeto.
- `config`: Contém a classe `SecurityConfig` que configura a segurança da aplicação.
- `controller`: Contém os controladores REST que manipulam as requisições HTTP.
- `dto`: Contém classes de transferência de dados (DTO) para comunicação com o exterior.
- `entity`: Contém a classe de entidade `User` que representa os usuários no sistema.
- `repository`: Contém a interface `UserRepository` para interagir com o banco de dados.
- `service`: Contém os serviços de negócios da aplicação.
- `util`: Contém utilitários como o `JwtTokenProvider`.
- `exception`: Contém classes para tratamento de exceções.
- `config`: Contém configurações de propriedades, como a configuração do servidor de e-mail.
- `resources`: Contém recursos como arquivos SQL para migrações de banco de dados e arquivos de configuração.

## Configuração de Segurança

A classe `SecurityConfig` é responsável pela configuração de segurança da aplicação. Ela utiliza a anotação `@EnableWebSecurity` para habilitar a segurança web e define vários beans:

- `authenticationManager`: Configura um gerenciador de autenticação.
- `bCryptPasswordEncoder`: Configura um codificador de senhas.
- `userDetailsService`: Configura um serviço de detalhes de usuário.
- `filterChain`: Configura as regras de autorização, desabilita CSRF e CORS, e define políticas de sessão.

## Controladores REST

O projeto inclui vários controladores REST que gerenciam endpoints HTTP:

- `APIYoutubeController`: Controlador para manipular solicitações relacionadas ao YouTube.
- `EmailController`: Controlador para manipular solicitações relacionadas ao envio de e-mails.
- `LoginController`: Controlador para manipular solicitações relacionadas à autenticação.
- `UserController`: Controlador para manipular solicitações relacionadas ao gerenciamento de usuários.

Cada controlador utiliza a anotação `@RestController` para indicar que ele responde a solicitações REST e a anotação `@RequestMapping` para mapear os URLs.

## DTOs (Data Transfer Objects)

As classes `SendEmailDTO`, `UserCreateDTO`, `UserLoginDTO` e `UserUpdateDTO` são classes de transferência de dados (DTO) usadas para representar informações transmitidas nas solicitações HTTP.

## Enumeração UserRole

A enumeração `UserRole` define os papéis dos usuários (ROLE_ADMIN e ROLE_USER) na aplicação.

## Entidade User

A classe `User` é uma entidade JPA que representa os usuários da aplicação. Ela implementa a interface `UserDetails` e define atributos como nome, senha, e-mail, data de nascimento e papel do usuário. Além disso, ela fornece métodos de autenticação e autorização.

## Repositório UserRepository

A interface `UserRepository` estende `JpaRepository` e define métodos para acessar o banco de dados relacionados aos usuários.

## Serviços

Os serviços são classes de negócios que contêm a lógica de aplicação. Os serviços incluem:

- `APIYoutubeService`: Serviço para interagir com a API do YouTube.
- `EmailService`: Serviço para enviar e-mails.
- `LoginService`: Serviço para autenticação de usuários.
- `UserService`: Serviço para gerenciamento de usuários.

## Tratamento de Exceções

A classe `GlobalExceptionHandler` define métodos para lidar com exceções específicas, retornando respostas HTTP adequadas.

## Dependências

O projeto utiliza várias dependências Maven, incluindo Spring Boot, Spring Security, Spring Data JPA, Flyway para migrações de banco de dados, Lombok para geração de código, e várias outras dependências relacionadas à integração com APIs, validação e segurança.

## Configurações Externas

O projeto faz uso de configurações externas por meio de variáveis de ambiente e propriedades no arquivo `application.yml`. Isso inclui configurações de e-mail, segredo JWT e outras configurações relacionadas à aplicação.

## Como Executar

Para executar o projeto, você precisará das seguintes configurações:

1. Configure as variáveis de ambiente `JWT_SECRET`, `EMAIL_USER`, `EMAIL_PASS`, `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASS` com os valores apropriados.

2. Certifique-se de ter um servidor PostgreSQL em execução e crie um banco de dados com o nome configurado em `DB_NAME`.

3. Execute o projeto Spring Boot.

## Dockerização do Projeto

Para facilitar a implantação e a execução do projeto, foi adicionado suporte para contêineres Docker. Isso permite que você empacote o aplicativo e todas as suas dependências em um contêiner isolado, garantindo que ele seja executado de maneira consistente em qualquer ambiente compatível com Docker.

### Dockerfile

O arquivo Dockerfile descreve como construir a imagem do contêiner Docker para o projeto. Ele é dividido em duas etapas:

1. **Build Stage**: Nesta etapa, usamos a imagem `openjdk:17.0.1-jdk-oracle` como base e copiamos o código-fonte do projeto para o contêiner. Em seguida, usamos o Maven para compilar o projeto e criar um arquivo JAR executável. A seguir, criamos um diretório chamado `target/dependency` e extraímos as dependências do JAR para esse diretório.

2. **Runtime Stage**: Nesta etapa, criamos uma nova imagem a partir da imagem base `openjdk:17.0.1-jdk-oracle`. Copiamos as dependências do estágio de compilação para o diretório `/app` no contêiner e configuramos o ponto de entrada para iniciar a aplicação Spring Boot.

Isso significa que, ao executar o contêiner Docker, a aplicação será executada com todas as dependências pré-configuradas, eliminando a necessidade de configurar manualmente o ambiente de desenvolvimento.

### docker-compose.yml

O arquivo `docker-compose.yml` é usado para definir e configurar os serviços Docker necessários para o projeto. Neste caso, ele descreve um serviço para um banco de dados PostgreSQL e configura algumas variáveis de ambiente relacionadas a esse banco de dados.

- **db**: Este serviço usa a imagem oficial do PostgreSQL e expõe o contêiner na porta `8011` para facilitar a conexão. As variáveis de ambiente definidas incluem o nome do usuário, senha e nome do banco de dados a serem usados.

- **volumes**: Um volume Docker chamado `pipocaagil-db-volume` é definido para persistir os dados do banco de dados. Isso garante que os dados do banco de dados não sejam perdidos quando o contêiner for parado.

- **networks**: Uma rede Docker chamada `pipocanetwork-network` é definida para permitir que os contêineres se comuniquem entre si.

### Como Executar com Docker

Para executar o projeto com Docker, siga estas etapas:

1. Certifique-se de ter o Docker instalado no seu sistema.

2. Defina as variáveis de ambiente necessárias no seu sistema ou no arquivo `.env` para as variáveis JWT_SECRET, EMAIL_USER, EMAIL_PASS, DB_HOST, DB_PORT, DB_NAME, DB_USER e DB_PASS com os valores apropriados.

3. Certifique-se de ter um servidor PostgreSQL em execução e configurado de acordo com as variáveis de ambiente definidas.

4. Execute o seguinte comando para construir a imagem Docker do projeto:

   ```shell
   docker build -t pipocaagil-app .

## Informações Adicionais

- Agradecimentos ao Ibson Cabral, dono do Podcast que nos deu a oportunidade de atuar com o SCRUM e aplicá-lo no dia-a-dia no desenvolvimento de software.
- Agradecimentos a equipe azul pela colaboração e empenho no desenvolvimento do projeto.
