## AUTOR
Jorge De Lima Rocha

# Sistema de Embalagem - Loja do Seu Manoel

API para cálculo otimizado de embalagens de pedidos da loja online do Seu Manoel.

## Descrição

Este projeto implementa uma API REST que recebe pedidos contendo produtos com suas dimensões e calcula a melhor maneira de embalar esses produtos em caixas de diferentes tamanhos, otimizando o espaço e minimizando o número de caixas utilizadas.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.2
- Spring Security + JWT
- Swagger/API
- JUnit 5
- H2 Database
- Docker

## Como Executar

### Usando Docker

## bash
docker-compose up

### Using Maven

## bash
mvn spring-boot:run

### API Documentation SWAGGER

## bash
http://localhost:8080/swagger-ui/index.html

### Teste API
### Teste para ver se API está funcionando
### 0. Teste API
GET /api/test

### Authentication
### A API utiliza autenticação JWT. Para obter um token:
## 1. Registrar o novo usuario:
POST /api/auth/register

EXEMPLO:

REQUEST:
```json
{
    "username": "JorgeUser",
    "password": "password123",
    "name": "Jorge",
    "email": "elipidio@example.com"
}
```
RESPONSE
```json
{
    "id": 2,
    "username": "JorgeUser",
    "password": "$2a$10$lYZIIvC7XJYjkZD5ykTc6e/Potxlcswxzbk052reF5S2MD1MHiV/C",
    "name": "Jorge",
    "email": "jorge@example.com",
    "roles": [
        "ROLE_USER"
    ],
    "active": true
}
```

## 2. Efetue login para obter o token::
POST /api/auth/login

EXEMPLO:

REQUEST:

```json
{
  "username": "JorgeUser",
  "password": "password123"
}
```

RESPONSE:

```json
{
    "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJKb3JnZVVzZXIiLCJyb2xlcyI6IlJPTEVfVVNFUiIsImlhdCI6MTc0ODcwNzQzMCwiZXhwIjoxNzQ4NzkzODMwfQ.D5eRpojsooNpyphN6exxYS7vu0hS84bedhuMSiS-TlMWo70vMZxt2Ncfm6pVhBJq",
    "type": "Bearer"
}
```
## 3. Use o token em solicitações subsequentes no cabeçalho de Autorização:
Authorization: Bearer {your-token}


## 4. Acessando o Empacotamento
POST /api/packaging
EXEMPLO:

REQUEST:

```json
{
  "orders": [
    {
      "id": "1",
      "products": [
        {
          "id": "1",
          "name": "Smartphone",
          "height": 15.0,
          "width": 7.5,
          "length": 1.0
        },
        {
          "id": "2",
          "name": "Laptop",
          "height": 2.5,
          "width": 35.0,
          "length": 25.0
        },
        {
          "id": "3",
          "name": "Headphones",
          "height": 10.0,
          "width": 10.0,
          "length": 5.0
        }
      ]
    },
    {
      "id": "2",
      "products": [
        {
          "id": "4",
          "name": "Monitor",
          "height": 35.0,
          "width": 55.0,
          "length": 10.0
        },
        {
          "id": "5",
          "name": "Keyboard",
          "height": 3.0,
          "width": 45.0,
          "length": 15.0
        }
      ]
    }
  ]
}

```

RESPONSE:

```json
{
  "results": [
    {
      "orderId": "1",
      "boxesUsed": [
        {
          "box": {
            "id": "1",
            "name": "Box 1",
            "height": 30.0,
            "width": 40.0,
            "length": 80.0
          },
          "products": [
            {
              "id": "2",
              "name": "Laptop",
              "height": 2.5,
              "width": 35.0,
              "length": 25.0
            },
            {
              "id": "3",
              "name": "Headphones",
              "height": 10.0,
              "width": 10.0,
              "length": 5.0
            },
            {
              "id": "1",
              "name": "Smartphone",
              "height": 15.0,
              "width": 7.5,
              "length": 1.0
            }
          ],
          "volumeOccupied": 2731.25,
          "efficiency": 28.45
        }
      ]
    },
    {
      "orderId": "2",
      "boxesUsed": [
        {
          "box": {
            "id": "2",
            "name": "Box 2",
            "height": 80.0,
            "width": 50.0,
            "length": 40.0
          },
          "products": [
            {
              "id": "4",
              "name": "Monitor",
              "height": 35.0,
              "width": 55.0,
              "length": 10.0
            },
            {
              "id": "5",
              "name": "Keyboard",
              "height": 3.0,
              "width": 45.0,
              "length": 15.0
            }
          ],
          "volumeOccupied": 21025.0,
          "efficiency": 65.7
        }
      ]
    }
  ]
}

```

### Main Endpoints
### Calcular Empacotamento 
### OBS: Sem se autenticar  usando o token gerado no end-point /api/auth/login 
### não irá conseguir obter retorno de dados, somente estando autenticado.
POST /api/packaging

## Banco de dados H2
## url
http://localhost:8080/h2-console/login.jsp

## config
Saved Settings: Generic H2 (Embedded)
Setting Name: Generic H2 (Embedded)
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:storedb
User Name: sa
Password: <vazio sem senha>

## querys de exemplo
select * from "user";

select * from user_roles ;

select * from app_user;

### DOCKER
## PARA COMPILAR O PROJETO E SUBIR ELE UTILIZE
docker-compose up --build

