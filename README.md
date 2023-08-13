# API-RESTfull

## Projeto:

API RESTful de CRUD de usuários e livros construido com proposito de portifólio, utilizando boas praticas de codificação e implementendo diversas tecnologias e padrões utilizadas no dia a dia em um time de Desenvolvimento. 

### O que foi feito:

- CRUD para Pessoas e Livros;
- Utilizar padrão VO e converção com Dozer nas classes de serviço;
- Integração com MySql;
- Implementar de suporte à Migrations com FlyWay;
- Content negotiation - prover JSON, YAML e XML;
- Implementar HATEOS (Hypermedia as the Engine of Application State);
- Adicionar suporte ao Swagger;
- Configurar Cross-origin;
- Autenticação com JWT e Spring Security;
- Blindar aplicação com Rest Assured, Testcontainers e JUnit 5
- Implementar busca paginada;
- Dockerizar aplicação;
  
[![Docker Hub Repo](https://img.shields.io/docker/pulls/yohanps/rest-udemy.svg)](https://hub.docker.com/repository/docker/yohanps/rest-udemy)

### Executando a aplicação:

- Garantir a integração com o MySQL. Caso não o faça, pode dar problema e o FlyWay não funcionará.
  - Crie um banco de dados local chamado `rest_book`, o username é `root` e password é `admin123`
- Atualize com sua IDE favorita.      
  - caso queria usar pelo terminal, execute `./mvnw clean package`  para linux ou `.\mvnw clean package ` para windows. Após isso execute na raiz: `java -jar <...caminhoParaSeuJar>`

- Após isso, você pode ver a documentação gerada pelo Swagger no ambiente local nessa url: http://localhost/swagger-ui/index.html


### Manipulando as requisições da API.

#### Antes de tudo, autentifique o usuário e use o token de acesso no header nas proximas requisições, exemplo: 

<font color="blue">POST</font> `http://localhost/auth/signin`
  
```
 {
  "username": "yohan",
  "password": "admin1234"
}
```

Essa requisição vai retornar um objeto semelhante a esse:

```
{
    "username": "yohan",
    "authenticated": true,
    "created": "2023-08-12T15:29:43.619+00:00",
    "expiration": "2023-08-12T16:29:43.619+00:00",
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5b2hhbiIsInJvbGVzIjpbIkFETUlOIiwiTUFOQUdFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0IiwiZXhwIjoxNjkxODU3NzgzLCJpYXQiOjE2OTE4NTQxODN9.UuJCp_Eft4aAiuH6GGNL7IOfiwPpkqhs1tXBSKeb7_k",
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5b2hhbiIsInJvbGVzIjpbIkFETUlOIiwiTUFOQUdFUiJdLCJleHAiOjE2OTE4NjQ5ODMsImlhdCI6MTY5MTg1NDE4M30.x3IvAkCOVgWjG0Aqyny08HdcU_ruhMbEQqQ5mV3xGLg"
}
```
Print do token no header em uma requisição:

![printbearer](https://github.com/YohanDevPs/API-RESTful/assets/87953006/40a517dc-eb5d-4c6b-b63c-f79edd47318d)



#### Exemplo de requisição <font color="blue">GET</font> de paginação de livros e seu JSON de saída.

Requisição: <font color="blue">GET</font> `http://localhost/api/books/v1?page=0&direction=asc&limit=3`

<details>
  <summary>Saída</summary>

#### Objeto de resposta
```
{
    "_embedded": {
        "bookVOList": [
            {
                "id": 345,
                "author": "Christoforo Mount",
                "launchDate": "2022-11-30T03:00:00.000+00:00",
                "price": 126.82,
                "title": "'burbs, The",
                "_links": {
                    "self": {
                        "href": "http://localhost/api/books/v1/345"
                    }
                }
            },
            {
                "id": 99,
                "author": "Bellina Vasiliev",
                "launchDate": "2023-06-02T03:00:00.000+00:00",
                "price": 76.25,
                "title": "100 Ways to Murder Your Wife (Sha qi er ren zu)",
                "_links": {
                    "self": {
                        "href": "http://localhost/api/books/v1/99"
                    }
                }
            },
            {
                "id": 292,
                "author": "Fredi Blenkensop",
                "launchDate": "2023-02-09T03:00:00.000+00:00",
                "price": 60.50,
                "title": "16 Acres",
                "_links": {
                    "self": {
                        "href": "http://localhost/api/books/v1/292"
                    }
                }
            }
        ]
    },
    "_links": {
        "first": {
            "href": "http://localhost/api/books/v1?limit=3&direction=asc&page=0&size=3&sort=title,asc"
        },
        "self": {
            "href": "http://localhost/api/books/v1?page=0&limit=3&direction=asc"
        },
        "next": {
            "href": "http://localhost/api/books/v1?limit=3&direction=asc&page=1&size=3&sort=title,asc"
        },
        "last": {
            "href": "http://localhost/api/books/v1?limit=3&direction=asc&page=171&size=3&sort=title,asc"
        }
    },
    "page": {
        "size": 3,
        "totalElements": 515,
        "totalPages": 172,
        "number": 0
    }
}
```
</details>

## EXTRA: Front-end em ReactJs

### Considerações

O client está muito simples e talvez seja melhorado com o tempo, mas por enquanto está aqui para provar que também sei um pouco de front-end. 

#### Print
![image](https://github.com/YohanDevPs/API-RESTful/assets/87953006/c66abacd-ebc0-4b2d-b8a1-2f0da6f25afd)


