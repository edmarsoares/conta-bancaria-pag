# conta-bancaria-pag
Api responsável por gerir uma conta bancária

# Tecnologias
    - postgres Sql
    - spring boot 
    - docker-compose
    - java 11
    - junit

# Docker

### Subindo uma instância do postgres e pgadmin

Entrar na pasta **src/docker/postgres** pelo terminal e aplicar o seguinte comando:

```
docker-compose -f docker-compose.yml up -d

```

Após aplicar o arquivo compose, acessar a url [http://localhost:16543](http://localhost:16543/) do pgAdmin ;

Crie uma base de dados de nome `contabancaria`

No arquivo **properties**, setar a propriedade **spring.jpa.hibernate.ddl-auto=create** para criar as tabelas automáticas. **Obs.:** Essa propriedade só deve ser usada para fins acadêmicos para agilizar o desenvolvimento; 

# Rodar a aplicação com dockerfile
