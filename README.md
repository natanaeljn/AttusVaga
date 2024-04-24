<h1 align="center">
  Attus API Rest
</h1>

<p align="center">

</p>

Api criada para vaga de desenvolvedor Java, buscando o gerenciamento de pessoas com possiveis endereços alocados.



## Tecnologias
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Banco H2](https://www.h2database.com/html/main.html)
- [Junit](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)


## Práticas adotadas

- SOLID
- API REST
- Consultas com Spring Data JPA
- Injeção de Dependências
- Tratamento de respostas de erro
- Geração automática do Swagger 
- Testes Unitarios


## Como Executar

- Clonar repositório git
- Construir o projeto:
```
$ ./mvnw clean package
```
- Executar a aplicação:
```
$ java -jar target/Sg-0.0.1-SNAPSHOT.jar
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

##  Endpoints 

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Postman](https://www.postman.com/downloads/):

- Criar Pessoa  
```
$ http POST :8080/pessoa/criarPessoa

[
 {
    
    "nome": "teste",
    "dataNascimento": "2023-09-25"

}
]
```

- Editar Pessoa
```
$ http POST :8080/pessoa/editarPessoa?nome=teste
[
 {
    
    "nome": "testeEditado",
    "dataNascimento": "2023-09-25"

}
]
```

- Consultar por Nome
```
$ http GET :8080/pessoa/consultarNome?nome=teste

[
{
        "id": 1,
        "nome": "teste",
        "dataNascimento": "2023-09-25",
        "endereco": []
    }
]
```
- consultar por Id
```
$ http GET :8080/pessoa/consultarPessoa?id=1

[
   {
        "id": 1,
        "nome": "teste",
        "dataNascimento": "2023-09-25",
        "endereco": []
    }
]
```
- Salvar novo endereço
```
$ http POST :8080/endereco/criarEndereco?idPessoa=1

[
   {
    "id": 1,
    "logradouro": "teste",
    "cep": "2222222",
    "numero": 510,
    "cidade": "2222222",
    "principal": true
}
]
```
- Listar Endereços : 
```
$ http GET :8080/endereco/listarEnderecos?idPessoa=1

[
  {
        "id": 1,
        "logradouro": "teste",
        "cep": "2222222",
        "numero": 510,
        "cidade": "2222222",
        "principal": true
    }
]

- Listar Endereço Principal : 
```
$ http GET :8080/endereco/listarEnderecoPrincipal?idPessoa=1

[
  {
        "id": 1,
        "logradouro": "teste",
        "cep": "2222222",
        "numero": 510,
        "cidade": "2222222",
        "principal": true
    }
]
```


