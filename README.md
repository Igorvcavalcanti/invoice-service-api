## 📄 Invoice Service API

API REST para gerenciamento de clientes, sessões de serviço e geração de invoices semanais, ideal para profissionais autônomos como cleaners, gardeners, handymen, etc., que trabalham por hora e recebem semanalmente.

Este projeto permite cadastrar clientes, registrar horas trabalhadas, gerar invoices automaticamente e atualizar o status do pagamento.

## 🚀 Tecnologias Utilizadas

Java 17

Spring Boot 3

Spring Web

Spring Data JPA

H2 Database

Lombok

Maven

Insomnia/Postman (testes)

Tomcat embutido

## 🧱 Arquitetura do Projeto

```text
src/main/java/com/igorcavalcanti/invoice_service_api
├── controller
│   ├── ClientController
│   ├── ServiceSessionController
│   └── InvoiceController
│
├── dtos
│   ├── request
│   │   ├── CreateClientRequest
│   │   ├── CreateServiceSessionRequest
│   │   └── GenerateInvoiceRequest
│   │
│   └── response
│       ├── ClientResponse
│       ├── ServiceSessionResponse
│       ├── InvoiceItemResponse
│       └── InvoiceResponse
│
├── entity
│   ├── Client
│   ├── ServiceSession
│   └── Invoice
│
├── exception
│   └── GlobalExceptionHandler
│
├── repository
│   ├── ClientRepository
│   ├── ServiceSessionRepository
│   └── InvoiceRepository
│
└── service
    ├── ClientService
    ├── ServiceSessionService
    └── InvoiceService

▶️ Como Rodar o Projeto
Pré-requisitos

Java 17 instalado

Maven instalado

IDE com suporte a Lombok (IntelliJ recomendado)
```
Rodar via terminal
```text
mvn spring-boot:run
```

Após iniciar, a API estará disponível em:

```text
http://localhost:8080
```
📌 Endpoints

A seguir estão todos os endpoints do sistema com exemplos de JSON para facilitar os testes.

👤 1. CLIENTS
➕ Criar cliente

POST /api/clients
```text
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+44 7700 900000",
  "address": "10 Downing Street, London",
  "hourlyRate": 25.00
}
```

📋 Listar clientes

GET /api/clients


🔍 Buscar cliente por ID

GET /api/clients/1


✏️ Atualizar cliente

PUT /api/clients/1
```text
{
  "name": "John Doe Updated",
  "email": "john.updated@example.com",
  "phone": "+44 7700 900001",
  "address": "221B Baker Street, London",
  "hourlyRate": 30.00
}
```
🗑️ Deletar cliente

DELETE /api/clients/1

🕒 2. SERVICE SESSIONS
➕ Registrar sessão

POST /api/sessions
```text
{
  "clientId": 1,
  "date": "2025-12-02",
  "hoursWroked": 3.00,
  "description": "Weekly cleaner"
}
```
📋 Listar sessões

GET /api/sessions?clientId=1

🧾 3. INVOICES
🧮 Gerar invoice

POST /api/invoices/generate
```text
{
  "clientId": 1,
  "periodStart": "2025-12-01",
  "periodEnd": "2025-12-07"
}
```

🔍 Buscar invoice por ID

GET /api/invoices/1

📋 Listar invoices por cliente

GET /api/invoices?clientId=1

✉️ Atualizar status

PATCH
/api/invoices/1/status?status=SENT

Status possíveis:

PENDING

SENT

PAID

🧮 Exemplo de Invoice Gerado
```text
{
  "id": 1,
  "clientId": 1,
  "clientName": "John Doe",
  "periodStart": "2025-12-01",
  "periodEnd": "2025-12-07",
  "totalHours": 9.00,
  "totalAmount": 225.00,
  "status": "PENDING",
  "items": [
    {
      "sessionId": 1,
      "date": "2025-12-02",
      "hoursWorked": 3.00,
      "description": "Weekly cleaner"
    },
    {
      "sessionId": 2,
      "date": "2025-12-03",
      "hoursWorked": 3.00,
      "description": "Weekly cleaner"
    },
    {
      "sessionId": 3,
      "date": "2025-12-04",
      "hoursWorked": 3.00,
      "description": "Weekly cleaner"
    }
  ]
}
```
🛠️ Tratamento Global de Erros

A API utiliza @RestControllerAdvice:

400 – Bad Request

404 – Not Found

409 – Conflict

500 – Internal Server Error

Formato exemplo:
```text
{
  "timestamp": "2025-12-07T19:20:56.387Z",
  "status": 400,
  "error": "Bad Request",
  "path": "/api/invoices/generate"
}
```

🧭 Possíveis Melhorias Futuras

Autenticação com JWT

Exportar invoice em PDF

Dashboard de relatórios

Suporte a múltiplos profissionais

Envio automático de invoice por e-mail

Swagger/OpenAPI

Docker Compose para subir banco + API

