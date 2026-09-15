# 🛒 Spring Boot E-Commerce REST API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x%2F4.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

A robust, production-ready backend RESTful API service for an E-Commerce platform built with **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**. Designed with clean layered architecture, supporting product lifecycle management and multipart image storage.

---

## 🌟 Key Features

- **Layered Architecture**: Clear separation of concerns with Controller, Service, and Repository layers.
- **RESTful Endpoints**: Full CRUD endpoints adhering to REST standards and HTTP status codes.
- **Multipart File Upload**: Upload and store product image files directly as binary `@Lob` data.
- **Database Persistence**: Automatic schema management and ORM with Spring Data JPA and Hibernate.
- **Cross-Origin Resource Sharing (CORS)**: Pre-configured `@CrossOrigin` for seamless frontend integration (React, Next.js, Angular, Vue).
- **Lombok Integration**: Cleaner code with automated getters, setters, constructors, and builders.

---

## 🏗️ Architecture & Flow

```
[ Client (Browser / Postman / Mobile) ]
                 |
                 v   HTTP Requests (JSON / Multipart)
[ Controller Layer (ProductController, HelloController) ]
                 |
                 v   Business Logic & Validation
[ Service Layer (ProductService) ]
                 |
                 v   Data Access & CRUD
[ Repository Layer (ProductRepo / Spring Data JPA) ]
                 |
                 v   Hibernate / JDBC
[ PostgreSQL Database (Database: akshat, Table: product) ]
```

---

## 🛠️ Tech Stack

| Component | Technology | Version / Notes |
| :--- | :--- | :--- |
| **Language** | Java | 21 (LTS) |
| **Framework** | Spring Boot | Web MVC, Data JPA |
| **Database** | PostgreSQL | Relational DB |
| **ORM** | Hibernate | Automated DDL schema updates |
| **Boilerplate Reduction** | Project Lombok | Annotations (`@Data`, `@AllArgsConstructor`, etc.) |
| **Build Tool** | Apache Maven | Wrapper included (`mvnw` / `mvnw.cmd`) |

---

## 📡 API Endpoints

### Base URL: `http://localhost:8080/api`

| Method | Endpoint | Description | Status Codes |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/products` | Retrieve all products | `200 OK` |
| `GET` | `/api/product/{id}` | Retrieve a product by ID | `200 OK`, `404 NOT FOUND` |
| `POST` | `/api/product` | Add a new product with image (Multipart) | `201 CREATED`, `500 INTERNAL_SERVER_ERROR` |

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

Update your database credentials in `src/main/resources/application.properties` (or set environment variables):

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

### 1. Get All Products
```bash
curl -X GET http://localhost:8080/api/products
```

### 2. Get Product By ID
```bash
curl -X GET http://localhost:8080/api/product/1
```

### 3. Add Product with Image (`multipart/form-data`)

In **Postman**:
1. Set method to `POST` and URL to `http://localhost:8080/api/product`.
2. Go to **Body** -> select **form-data**.
3. Add a key named `product` of type **Text** (Content-Type: `application/json`) with:
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
4. Add a key named `image` of type **File** and select an image file from your disk.
5. Click **Send**.

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
│   │   │   │   └── ProductController.java
│   │   │   ├── model/             # JPA Entities
│   │   │   │   └── Product.java
│   │   │   ├── repo/              # Spring Data JPA Repositories
│   │   │   │   └── ProductRepo.java
│   │   │   ├── services/          # Business logic
│   │   │   │   └── ProductService.java
│   │   │   └── EcommerceApplication.java # Spring Boot entrypoint
│   │   └── resources/
│   │       └── application.properties    # Database & app configuration
│   └── test/                             # Unit and integration tests
├── .gitattributes
├── .gitignore                     # Git ignore rules
├── mvnw / mvnw.cmd                # Maven wrapper scripts
├── pom.xml                        # Maven dependencies & build configuration
└── README.md                      # Project documentation
```

---

## 📌 Roadmap & Upcoming Features

- [ ] Product Update (`PUT /api/product/{id}`) and Delete (`DELETE /api/product/{id}`)
- [ ] Product Image retrieval endpoint (`GET /api/product/{id}/image`)
- [ ] Keyword & Category search filters (`GET /api/products/search?keyword=...`)
- [ ] Authentication & Authorization with Spring Security and JWT
- [ ] Pagination and sorting support

---

## 👨‍💻 Author

**Akshat**
- GitHub: [@akshatXD-hash](https://github.com/akshatXD-hash)

---

## 📄 License

This project is licensed under the MIT License - feel free to use it for learning and development!
