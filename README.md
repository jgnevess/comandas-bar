# 📦 Sistema de Vendas e Comandas para Bar

Sistema completo para controle de vendas, com autenticação por perfil, controle de estoque, comandas e geração de relatórios.

---

## 🧑‍💼 Perfis de Usuário

### 👨‍💼 Admin
- Cadastrar, editar e inativar produtos
- Controlar movimentações de estoque
- Consultar produtos (ativos e inativos)
- Gerar relatórios de vendas
- Acessar todos os recursos do sistema

### 🧾 Seller (Vendedor)
- Criar, editar, finalizar e cancelar comandas
- Consultar produtos ativos
- Registrar vendas

---

## ⚙️ Funcionalidades

- 🔐 Login com autenticação via JWT
- 📦 Cadastro e gerenciamento de produtos
- 🧮 Registro de entradas/saídas de estoque
- 🛒 Criação e controle de comandas
- 💰 Fechamento de vendas com forma de pagamento
- 📊 Geração de relatórios por período
- 🎨 Tema escuro e layout responsivo

---

## 🧠 Tecnologias

### 🔙 Backend
- Java 17
- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate

<!-- ### 🔜 Frontend
- React + TypeScript
- Bootstrap 5
-->
---

## 🗃️ Estrutura de Dados

- **User**: id, username, password, role
- **Product**: id, code, description, costPrice, sellingPrice, offPrice, category, isActive
- **Storage**: id, product, quantity, movementDate
- **OrderItem**: productId, quantity, unitPrice
- **Order**: id, clientName, orderDateTime, items, totalPrice, paymentType, status
- **Sale**: id, orderId

---

## 📚 Documentação

📄 Toda a documentação técnica (requisitos, regras de negócio, diagramas) está disponível na pasta [`/docs`](./docs) ou no arquivo `Documentacao_Tecnica_Sistema_Vendas.pdf`.

---

## 🚀 Como rodar o projeto

✅ Pré-requisitos

- Java 17 instalado
- Maven 3.8+ instalado
- Banco de dados PostgreSQL 

### Backend (Spring Boot)
```bash
./mvnw spring-boot:run
