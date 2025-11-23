

# 📊 Market Intelligence


Bem-vindo ao **Market Intelligence**, um sistema Java Spring Boot para análise de produtos, métricas de vendas e integração com APIs externas como **DummyJSON**.  
O projeto utiliza uma arquitetura modular, boas práticas de código, testes automatizados, cache e tratamento global de exceções.

---

## 📌 Sobre o Projeto
O objetivo do Market Intelligence é processar e analisar dados de produtos, carrinhos e usuários das APIs externas, permitindo cálculos como:

- Ticket médio global e por usuário
- Produto mais vendido
- Produto menos vendido
- Comparações entre dados remotos e dados armazenados localmente

Além disso, o projeto permite cadastrar usuários localmente com base nas informações fornecidas pela API DummyJSON.

---

## 🏗️ Arquitetura do projeto
### Layered Architecture
Arquitetura escolhida por ser a que já tenho experiênca.

## 🚀 Funcionalidades

### ✔️ Usuários
- Criar usuário a partir do DummyJSON
- Deletar usuários

### ✔️ Produtos e Carrinhos
- Consultar produtos mais vendidos
- Consultar produtos menos vendidos
- Calcular ticket médio global
- Comparar ticket médio remoto × local

### ✔️ Outras funcionalidades
- Integração com DummyJSON e FakeStoreAPI
- Cache interno para otimização de chamadas
- Mapeamento de DTOs com **MapStruct**
- Testes unitários + JaCoCo

---

## 🏗️ Estrutura do Projeto

```text
src/main/java/br/com/ronaldo/market_intelligence
│
├── application
│   ├── controller
│   │   └── DummyJsonController
│   ├── dto
│   │   ├── BestSellingProductDto
│   │   ├── TicketMedioResponseDto
│   │   ├── UserRequestDto
│   │   └── UserResponseDto
│   └── ApiExceptionHandler
│
├── domain
│   ├── adapter
│   ├── entity
│   ├── exception
│   ├── model
│   └── repository
│
├── service
│   ├── cart
│   ├── product
│   └── user
│
├── infrastructure
│   ├── cache
│   ├── client
│   ├── mapper
│   └── repository
│
└── MarketIntelligenceApplication
```

---

## 📡 Endpoints Principais

### 👤 Criar Usuário
**POST /api/create_user**

Busca o usuário pelo *email* na DummyJSON e grava no banco local.

### ❌ Deletar Usuário
**DELETE /api/delete_user/{id}**

Exclui usuário a partir do ID.

### 🧮 Ticket Médio
**GET /api/ticket_medio**  

Compara ticket médio da DummyJSON com o local.

### ⭐ Produto Mais / Menos Vendido
**GET /api/product_selling_analyses**  

Identifica o produto mais e o menos vendido.

---

## 🧪 Testes e Cobertura

### Executar os testes:

mvn test


### Gerar relatório do Jacoco:

O relatório será gerado em: /target/site/jacoco/index.html

---

## 🛠️ Tecnologias
- Java 21
- Spring Boot 3
- Spring Web
- Spring Validation
- MapStruct
- H2 Database
- JPA / Hibernate
- Maven
- JUnit 5
- Mockito
- JaCoCo

---

## 🛡️ Tratamento Global de Erros

O projeto possui um `@ControllerAdvice` para:

- Capturar erros de validação
- Notificar parâmetros obrigatórios ausentes
- Tratar *MethodArgumentTypeMismatchException*
- Tratar exceções customizadas
- Retornar respostas JSON padronizadas ao cliente

---

## ▶️ Como executar o projeto

### 🔧 1. Pré-requisitos
- JDK 21
- Maven 3+

### 🚀 2. Rodar a aplicação 
mvn spring-boot:run

### 🌐 3. Acessar Aplicação  
http://localhost:8080

Swagger UI:  
http://localhost:8080/swagger-ui/index.html

Swagger Docs:

http://localhost:8080/v3/api-docs

Baixar Documentação:

http://localhost:8080/v3/api-docs.yaml

Acessar banco de dados H2

http://localhost:8080/h2

usuário: admin

senha: admin@123


---

## 📄 Licença
Projeto de estudo — uso livre.

---

## 🤝 Autor
Desenvolvido por **Ronaldo Gonçalves da Silva**.

