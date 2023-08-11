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
  - Crie um banco de dados local chamado `rest_udemy`, o username é `root` e password é `admin123`
- Atualize com sua IDE favorita.      
  - caso queria usar pelo terminal, execute `./mvnw clean package`  para linux ou `.\mvnw clean package ` para windows. Após isso execute na raiz: `java -jar <...caminhoParaSeuJar>`


### Manipulando as requisições da API.

#### Antes de tudo, autentifique o usuário e use o token de acesso no header nas proximas requisições, exemplo: 

<font color="blue">POST</font> `{{baseUrl}}/auth/signin`
  
```
 {
  "username": "leandro",
  "password": "admin1234"
}
```

Essa requisição vai retornar um objeto semelhante a esse:

```
{
    "username": "leandro",
    "authenticated": true,
    "created": "2023-08-11T22:17:09.360+00:00",
    "expiration": "2023-08-11T23:17:09.360+00:00",
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWFuZHJvIiwicm9sZXMiOlsiQURNSU4iLCJNQU5BR0VSIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3QiLCJleHAiOjE2OTE3OTU4MjksImlhdCI6MTY5MTc5MjIyOX0.NWiUVBKJJ8VZKx212RTek5a_DSuk_wvKhfIeBu9-veY",
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWFuZHJvIiwicm9sZXMiOlsiQURNSU4iLCJNQU5BR0VSIl0sImV4cCI6MTY5MTgwMzAyOSwiaWF0IjoxNjkxNzkyMjI5fQ.o3r0mf1DQNFgb-wjkyPgagHkTPt28NSQMN2bY6kw_Io"
}
```
Print do token no header em uma requisição:

![printbearer](https://github.com/YohanDevPs/API-RESTful/assets/87953006/40a517dc-eb5d-4c6b-b63c-f79edd47318d)


#### Requisição <font color="blue">GET</font>** de paginação de livros e seu JSON de saída.

URL <font color="blue">GET</font> `{{baseUrl}}/api/books/v1?page=0&direction=asc&limit=3`

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
