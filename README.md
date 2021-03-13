# Single Digit

Projeto de API criada para cadastramento de usuário e identificação do dígito único a partir da soma dos dígitos de um número.

**Especificação da API:** [Swagger DOC](https://single-digit-project.herokuapp.com/api/v1/swagger-ui.html#/)

É Possível gerar as chaves RSA através do link a seguir: [devglan](https://www.devglan.com/online-tools/rsa-encryption-decryption)

_Obs:_ A chave deve ter **2048 bit**

```
            :: SINGLE DIGIT APPLICATION ::

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

## 1 - Ferramentas e Configurações

### 1.1- IntelliJ

**Importação do Projeto (Git)**

Após abrir o IntelliJ, importar projeto do Git:

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

## 2 - Arquitetura do Sistema

Este projeto segue um padrão arquitetural em camadas [[1](https://www.oreilly.com/library/view/software-architecture-patterns/9781491971437/ch01.html),[2](https://en.wikipedia.org/wiki/Multitier_architecture)] para fornecer uma API REST [[3](https://dzone.com/articles/intro-rest),[4](https://www.quora.com/What-are-RESTful-APIs-and-how-do-they-work),[5](https://blog.caelum.com.br/rest-principios-e-boas-praticas/)]. A camada mais externa do sistema (_Controller_) implementa os serviços REST (JAX-RS), tendo esta camada a responsabilidade de validar os dados de entrada.

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

Para executar aplicação basta executar o seguinte comando:

```sh
$ mvn spring-boot:run
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
├── test
│   ├── java/com/challange/singledigit
└── └── └── service                                  -> classes de teste da camada de negócio (xxxxService.java)
```

## 3 - Testes
### Testes Unitários
Foram criados testes unitários para as classes de negócio (*Service.java) dos serviços, a fim de garantir a corretude acerca da lógica de negócio envolvendo estas classes. Estas classes de testes foram criadas usando JUnit e Mockito (para 'mockar' as classes dependentes). Para rodar todos os testes unitários do projeto usando o Maven, executar o seguinte comando:
```
$ mvn clean test
```
## 4 - Geração da Build e Deploy da Aplicação
O deploy da aplicação foi feito no servidor do [Heroku](https://www.heroku.com/) onde foi necessário
realizar o download do [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli) para subir a aplicação no servidor.

### 4.1 - Deploy da Aplicação
Após instalar o Heroku CLI é necessário realizar os seguintes passos:

Abrir o CMD e ir ao diretório do projeto
````
cd /d D:\Documentos\Single-Digit
````
Logo após é necessário realizar o login no heroku
````
$ heroku login
````
Use o Git para clonar o código-fonte do projeto single-digit para sua máquina local.
````
$ heroku git:clone -a single-digit-project
$ cd single-digit-project
````
Em caso de alteração no código ou necessidade de deploy, relizar os seguintes comandos
````
$ git add .
$ git commit -am "your commit description"
$ git push heroku main
````
### 4.2- Gerar a Build do Aplicação
````
$ mvn clean package
````
## 5- Sobre o calculo do dígito único
A forma como o calculo do dígito único é feito utiliza de duas estratégias:

A primeira delas diz respeito a números que tem como máximo valor a variável Long.MAX_VALUE (9.223.372.036.854.775.807) utilizando um algoritmo de complexidade O(1) para realizar o cálculo.
Esse algoritmo consiste no seguinte conceito "A soma dos dígitos até o dígito único em Java também pode ser calculada dividindo diretamente o número por 9. Se o número for divisível por 9, então é a soma dos dígitos até que o dígito único seja 9, caso contrário, é o número% 9".
````
public long singleDigit(long number) {
     return number % 9 ;
}
````
A segunda estratégia é usada para números que tem valor maior que a variável Long.MAX_VALUE utilizando um algoritmo de complexidade O(n) com Stream API para realizar o cálculo.
````
public Integer digitalSum(String number) {
     while (number.length() > 1) {
            number = Integer.toString(
                                number.chars()
                                      .map(Character::getNumericValue)
                                      .sum());
        }
        return Integer.parseInt(number);
}
````