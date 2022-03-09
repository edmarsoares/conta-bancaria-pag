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
dockerFile

```Docker
FROM openjdk:11
WORKDIR /usr/src/myapp
EXPOSE 8081
COPY ./api-contaBancaria-pagSeguro-0.0.1-SNAPSHOT.jar .

```

# Job 

Foi Desenvolvido um job que busca todas as contas com cheque especial ativo e aplica a taxa de juros. o job ta configurado pra rodar a cada 1 minuto para realização de testes. Caso precise mudar alterar a seguinte constante:

```java
private final static String HORARIO_EXECUCAO = "0 0/30 * * * ? 

```

Neste caso o horário de execução está para rodar a cada 30 minutos;

# Documentação

link do swagger [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

# Regras de negócio

1. Não pode realizar saque com valor maior que o saldo da conta;

2. Ao zerar o saldo da conta, poderá sacar automaticamente o valor liberado do cheque especial ;

3. Se o deposito na conta for maior ou igual ao saldo acumulado do cheque, o mesmo será desabilitado e a diferença será depositada na conta, caso contrário,
para cada depósito, o saldo acumulado do cheque é descontado.


# Melhorias

1. Refatorar regra de cheque especial
2. Adicionar Controller advice para gerenciamento de exception
3. Adicionar validações de campos
4. Adicionar testes de integração
5. Adicionar validação pra arredondar valores doubles muito grandes. **Ex.: 341.47222941330676**