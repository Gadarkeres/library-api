Library é um projeto de biblioteca com a finalidade de gerenciamento de usuarios, livros e emprestimos.
Este projeto utiliza java 21 com postgres como banco de dados e spring boot para desenvolvimento.

## Pre-requisitos

- Java 21
- Postgres (16)
- Spring Boot
- git

## Instalação

- Clonar o repositório

```bash
git clone https://github.com/Gadarkeres/library-api.git
cd library-api
code .
```

- Instalar as dependências

```bash
mvn install
```
- Crie um banco de dados Postgres com o nome "library"
- Após criar o banco de dados, configure as variáveis de ambiente como abaixo:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/library // substitua pela sua url
spring.datasource.username=postgres // substitua pelo seu usuario
spring.datasource.password=root // substitua pela sua senha
```

- Este projeto utiliza Flyway para gerenciamento de migrations, será criado todas as tabelas e será populado automaticamente somente a tabela de livros, mas não deemprestimos incialmente.
## Execução

- Executando os testes

```bash
mvn test
```

- Executando o projeto

```bash
mvn spring-boot:run
``` 

##Acessando a API:

- A API estará disponível em http://localhost:8080.
- A documentação da API pode ser acessada via Swagger em: http://localhost:8080/swagger-ui/index.html.
