# 🛒 Spring Boot E-Commerce REST API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x%2F4.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

A robust, production-ready backend RESTful API service for an E-Commerce platform built with **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**. Designed with clean layered architecture, supporting product lifecycle management, multipart image storage, and transactional order placement with real-time inventory management.

---

## 🌟 Key Features

- **Layered Architecture**: Clear separation of concerns with Controller, Service, and Repository layers.
- **Product Management**: Full CRUD operations for products, including keyword search and image upload/retrieval.
- **Order Processing & Inventory Control**:
  - Multi-item checkout with automatic stock verification and deduction.
  - Transactional consistency (`@Transactional`) guaranteeing atomic stock updates and order item persistence.
  - Automated unique order reference generation (e.g., `ORD...`).
  - Validation against out-of-stock items, unavailable products, and invalid quantities (`400` / `409` HTTP statuses).
- **Multipart File Upload**: Upload and store product image files directly as binary `@Lob` data.
- **Input Validation**: DTO-level validation with Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@Email`, `@Positive`, `@NotEmpty`).
- **Database Persistence**: Relational data modeling with Spring Data JPA and Hibernate (`Product`, `Order`, `OrderItem`).
- **Cross-Origin Resource Sharing (CORS)**: Pre-configured `@CrossOrigin` for seamless frontend integration (React, Next.js, Angular, Vue).
- **Lombok Integration**: Cleaner code with automated getters, setters, constructors, and builders.

---

## 🏗️ Architecture & Flow

```
[ Client (Browser / Postman / Mobile) ]
                 |
                 v   HTTP Requests (JSON / Multipart)
[ Controller Layer (ProductController, OrderController, HelloController) ]
                 |
                 v   Business Logic, Validation & Transactions
[ Service Layer (ProductService, OrderService) ]
                 |
                 v   Data Access & ORM Queries
[ Repository Layer (ProductRepo, OrderRepo / Spring Data JPA) ]
                 |
                 v   Hibernate / JDBC
[ PostgreSQL Database (Database: akshat | Tables: product, orders, order_item) ]
```

---

## 🛠️ Tech Stack

| Component | Technology | Version / Notes |
| :--- | :--- | :--- |
| **Language** | Java | 21 (LTS) |
| **Framework** | Spring Boot | Web MVC, Data JPA, Validation |
| **Database** | PostgreSQL | Relational DB |
| **ORM** | Hibernate | Automated DDL schema updates |
| **Boilerplate Reduction** | Project Lombok | Annotations (`@Data`, `@Getter`, `@Setter`, `@Builder`, etc.) |
| **Build Tool** | Apache Maven | Wrapper included (`mvnw` / `mvnw.cmd`) |

---

## 📡 API Endpoints

### Base URL: `http://localhost:8080/api`

#### 📦 Products

| Method | Endpoint | Description | Status Codes |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/products` | Retrieve all products | `200 OK` |
| `GET` | `/api/product/{id}` | Retrieve a product by ID | `200 OK`, `404 NOT FOUND` |
| `POST` | `/api/product` | Add a new product with image (Multipart) | `201 CREATED`, `500 INTERNAL_SERVER_ERROR` |
| `PUT` | `/api/product/{id}` | Update an existing product with image | `200 OK`, `400 BAD REQUEST`, `404 NOT FOUND` |
| `DELETE` | `/api/product/{id}` | Delete a product by ID | `200 OK`, `404 NOT FOUND` |
| `GET` | `/api/product/{id}/image` | Retrieve a product's image data | `200 OK`, `404 NOT FOUND` |
| `GET` | `/api/product/search?keyword={kw}` | Search products by keyword (name/desc/brand) | `200 OK` |

#### 🛍️ Orders

| Method | Endpoint | Description | Status Codes |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/orders/place` | Place a new order with line items | `201 CREATED`, `400 BAD REQUEST`, `404 NOT FOUND`, `409 CONFLICT` |
| `GET` | `/api/orders` | Retrieve all orders with line item details | `200 OK` |

---

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed on your machine:
- **Java Development Kit (JDK 21+)**
- **PostgreSQL Server** (running on port `5432`)
- **Git**

### 1. Clone the Repository

```bash
git clone https://github.com/akshatXD-hash/learning-springboot.git
cd learning-springboot
```

### 2. Configure PostgreSQL Database

Create a database named `akshat` (or your preferred name) in PostgreSQL:

```sql
CREATE DATABASE akshat;
```  

Copy the template configuration and update your database credentials:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Then edit `src/main/resources/application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/akshat
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Build and Run the Application

Using the Maven Wrapper:

**Windows (PowerShell / CMD):**
```powershell
.\mvnw.cmd clean spring-boot:run
```

**macOS / Linux:**
```bash
./mvnw clean spring-boot:run
```

The application will start on port **`8080`**.

---

## 🧪 Testing with Postman / cURL

### 1. Products

#### Get All Products
```bash
curl -X GET http://localhost:8080/api/products
```

#### Get Product By ID
```bash
curl -X GET http://localhost:8080/api/product/1
```

#### Add Product with Image (`multipart/form-data`)
In **Postman**:
1. Method: `POST` | URL: `http://localhost:8080/api/product`
2. Body -> **form-data**:
   - `product` (Text / `application/json`):
     ```json
     {
       "name": "Sony WH-1000XM5",
       "description": "Wireless Noise Cancelling Headphones",
       "brand": "Sony",
       "price": 399.99,
       "category": "Electronics",
       "releaseDate": "2026-05-15",
       "available": true,
       "stockQuantity": 50
     }
     ```
   - `image` (File): Select image file from your disk.
3. Click **Send**.

#### Search Products by Keyword
```bash
curl -X GET "http://localhost:8080/api/product/search?keyword=Sony"
```

---

### 2. Orders

#### Place an Order (`POST /api/orders/place`)

##### Request:
```bash
curl -X POST http://localhost:8080/api/orders/place \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Alice Smith",
    "customerEmail": "alice@example.com",
    "items": [
      {
        "productId": 1,
        "quantity": 2
      }
    ]
  }'
```

##### Request Body Schema:
```json
{
  "customerName": "Alice Smith",
  "customerEmail": "alice@example.com",
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

##### Sample Success Response (`201 CREATED`):
```json
{
  "orderId": "ORD4F2B789A",
  "customerName": "Alice Smith",
  "customerEmail": "alice@example.com",
  "status": "PLACED",
  "orderDate": "2026-09-21",
  "items": [
    {
      "productName": "Sony WH-1000XM5",
      "quantity": 2,
      "totalPrice": 799.98
    }
  ]
}
```

> [!NOTE]
> When an order is placed, stock is atomically deducted from each product. If a product has insufficient stock or is unavailable, the order is rejected with `409 CONFLICT`, ensuring transactional consistency.

#### Get All Orders (`GET /api/orders`)
```bash
curl -X GET http://localhost:8080/api/orders
```

---

## 📂 Project Structure

```
Ecommerce/
├── .mvn/wrapper/                  # Maven wrapper binaries & properties
├── src/
│   ├── main/
│   │   ├── java/com/akshat/Ecommerce/
│   │   │   ├── controllers/       # REST API Endpoints
│   │   │   │   ├── HelloController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   └── ProductController.java
│   │   │   ├── model/             # JPA Entities & DTOs
│   │   │   │   ├── dto/
│   │   │   │   │   ├── OrderItemRequest.java
│   │   │   │   │   ├── OrderItemResponse.java
│   │   │   │   │   ├── OrderRequest.java
│   │   │   │   │   └── OrderResponse.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── OrderItem.java
│   │   │   │   └── Product.java
│   │   │   ├── repo/              # Spring Data JPA Repositories
│   │   │   │   ├── OrderRepo.java
│   │   │   │   └── ProductRepo.java
│   │   │   ├── services/          # Business logic & transaction handling
│   │   │   │   ├── OrderService.java
│   │   │   │   └── ProductService.java
│   │   │   └── EcommerceApplication.java # Spring Boot entrypoint
│   │   └── resources/
│   │       ├── application.properties.example # Sample configuration template
│   │       └── application.properties         # Database & app configuration
│   └── test/                                  # Unit and integration tests
├── .gitattributes
├── .gitignore                     # Git ignore rules
├── mvnw / mvnw.cmd                # Maven wrapper scripts
├── pom.xml                        # Maven dependencies & build configuration
└── README.md                      # Project documentation
```

---

## 📌 Roadmap & Upcoming Features

- [x] Product CRUD (Create, Read, Update, Delete)
- [x] Product Image upload and retrieval (`@Lob`)
- [x] Keyword search filter (`GET /api/product/search?keyword=...`)
- [x] Order Placement with transactional stock deduction
- [x] Order history and details retrieval (`GET /api/orders`)
- [ ] Category-based filtering
- [ ] Authentication & Authorization with Spring Security and JWT
- [ ] Pagination and sorting support for products and orders
- [ ] Payment gateway integration (Stripe / Razorpay)

---

## 👨‍💻 Author

**Akshat**
- GitHub: [@akshatXD-hash](https://github.com/akshatXD-hash)

---

## 📄 License

This project is licensed under the MIT License - feel free to use it for learning and development!
