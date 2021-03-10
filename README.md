# Single Digit

Projeto de API criada para cadastramento de usuário e identificação do dígito único a partir da soma dos dígitos de um número.

Especificação da API:(http://localhost:[port]/api/swagger-ui.html)

```
            :: FLEXPAG -  Arquivo Retorno Utilities ::

┌───────────────┐       ┌───┬────────────┐       ┌──────────────┐
│     API       │       │ R │            │       │              │
│ SINGLE DIGIT  │ <===> │ E │  Backend   │ <===> │      BD      │
├───────────────┤       │ S │  (Java)    │       │     (H2)     │
│               │       │ T │            │       │              │
└───────────────┘       └───┴────────────┘       └──────────────┘
```

Este projeto usa as seguintes tecnologias:
- [Java 11](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [SpringBoot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [Lombok](https://projectlombok.org/)
- [Swagger](https://swagger.io/solutions/api-documentation/)
- [JUnit](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [H2 Database](https://www.h2database.com/html/main.html)

## 1- Ferramentas e Configurações

### 1.1- IntelliJ

**Importação do Projeto (Git)**

Após abrir o Eclipse, importar projeto do Git:

1. _File_ -> _New..._ -> _Project from version control_ -> _URL_
2. URL: `https://gitlab.com/rodrigorjsf/Single-Digit.git` -> _Next_
3. Escolher _branch_ `main`
4. _Local destination_: `pasta-do-workspace`
5. _New Window_

**Configuração do Projeto**

1. Clicar com botão direito no projeto -> _Configure_ -> _Convert to Maven project_
2. Clicar com botão direito no projeto -> _Maven_ -> _Update Project..._
3. OK (incluir opção `Force Update os Snapshots/Releases`)

Incluir `Run Configurations`:

**Maven Build** (para gerar a _build_ do sistema)
- _New launch configuration_:
- _Name_: `single-digit [maven install]`
- _Base directory_: `single-digit`
- _Goals_: `install`

**Java Application** (para rodar a aplicação localmente)
- _New launch configuration_:
    - _Main_
        - _Name_: `single-digit [run]`
        - _Project_: `single-digit`
        - _Main class_: `com.challange.singledigit.SingledigitApplication`

Obs.: Antes de iniciar a implementação no projeto, execute a **_Maven Build_** (item 1) para baixar todas as dependências do projeto para seu repositório local do Maven (normalmente na pasta `~/.m2`) e deste forma evitar erros de compilação/_runtime_.

## 2- Arquitetura do Sistema

Este projeto segue um padrão arquitetural em camadas [[1](https://www.oreilly.com/library/view/software-architecture-patterns/9781491971437/ch01.html),[2](https://en.wikipedia.org/wiki/Multitier_architecture)] para fornecer uma API REST [[3](https://dzone.com/articles/intro-rest),[4](https://www.quora.com/What-are-RESTful-APIs-and-how-do-they-work),[5](https://blog.caelum.com.br/rest-principios-e-boas-praticas/)]. A camada mais externa do sistema (_Controller_) implementa os serviços REST (JAX-RS), tendo esta camada a responsabilidade de validar os dados de entrada, assim como realizar as restrições de segurança necessárias (autenticação/autorização) no acesso aos serviços disponibilizados.

A camada imediatamente abaixo (_Service_) é responsável pela execução da lógica de negócio de cada serviço, que incluem validações de negócio e transformação dos dados de entrada antes de atualizá-los.

A última camada (_Repository_) do modelo é responsável pelas funcionalidades diretas de acesso/atualização dos dados do sistema no banco de dados.

```
┌───────────────────┐
│ Controller (REST) │ JAX-RS 2.0 (JSR 311), Bean Validation 1.1 (JSR 349), JSON 1.0 (Jackson)
├───────────────────┤
│   Service         │ 
├───────────────────┤
│ Persistence (Rep) │ SpringJPA
└───────────────────┘
         ||
     ┌────────┐
     │   BD   │ H2
     └────────┘
```

O projeto foi desenvolvido usando a ferramenta [SpringBoot](https://thorntail.io/), que possibilita o empacotamento do servidor de aplicação (Tom Cat) juntamente com a build do sistema em um único arquivo `.jar`. Para isto, basta rodar o seguinte comando com o Maven:

```sh
$ mvn install
```

Para executar aplicação juntamente com o servidor de aplicação Wildfly, basta executar o seguinte comando:

```sh
$ java -jar nome-da-aplicacao.jar
```

A organização e significado de cada um dos pacotes do projeto segue abaixo:

```
src
├── main
│   ├── java/com/challange/singledigit
│   │   ├── config                                   -> classes que implementam a segurança e documentação Swagger
│   │   ├── controller                               -> classes da camada de serviço (xxxxController.java)
│   │   ├── exception                                -> classes de exceções e mappers
│   │   ├── model                                    -> classes de entidades/pojos/dtos
│   │   ├── repository                               -> classes da camada de persistência (xxxxRepository.java)
│   │   ├── service                                  -> classes da camada de negócio (xxxxService.java)
│   │   ├── util                                     -> classes utilitárias
│   ├── resources
│   │   └── application                              -> arquivo de propriedades para ambiente de homologacao do projeto
└── test
```