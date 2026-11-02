# 🍔 Foodzy - E-Commerce Backend API

## 📖 Project Description

Foodzy is a RESTful E-Commerce Backend application developed using spring boot.

It provides secure authentication using JWT and support E-Commerce operation such as.

- User Authentication
- Product Management
- Category Management
- Cart Management
- Order Management
- Payment Management
- Admin Dashboard

# 🚀 Features

## Authentication

- User Registration
- User Login
- JWT Authentication
- Role Based Authorization (USER / ADMIN)

## User

- Get Current User
- Update Profile
- Get User By Id
- Get All Users
- Delete User

## Category

- Create Category
- Update Category
- Delete Category
- Get Category By Id
- Get All Categories

## Product

- Create Product
- Update Product
- Delete Product
- Get Product By Id
- Get All Products
- Search Products
- Pagination Support

## Cart

- Add Item
- View Cart
- Update Quantity
- Remove Item
- Clear Cart

## Order

- Place Order
- Get My Orders
- Get Order By Id
- Cancel Order
- Update Order Status

## Payment

- Create Payment for an Order
- Cash on Delivery (COD) Support
- Razorpay Payment Gateway Integration
- Create Razorpay Order
- Verify Payment Signature
- Validate Payment Amount and Currency
- Check Payment Status
- Prevent Duplicate Transactions
- Get Payment By Order ID
- User-Based Payment Authorization

## Admin Dashboard

- Dashboard Summary
- Total Users
- Total Products
- Total Orders
- Total Revenue
- Recent Orders
- Top Selling Products

# 🛠 Tech Stack

### Backend

- Java 17
- Spring Boot
- Spring MVC
- Spring Security
- JWT
- Spring Data JPA
- Hibernate

### Database

- MySQL

### Documentation

- Swagger (OpenAPI)

### Build Tool

- Maven

---

# 📂 Project Structure

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Database
```

---

# 🗄 Database Entities

- User
- Category
- Product
- Cart
- CartItem
- Order
- OrderItem
- Payment

---

# 🔐 Security

- JWT Authentication
- Role Based Authorization
- BCrypt Password Encoding
- Spring Security

---

# ⚠ Exception Handling

- Resource Not Found Exception
- Duplicate Resource Exception
- Bad Request Exception
- Validation Exception
- Authentication Exception
- Access Denied Exception

---

# 📌 API Endpoints

## Authentication

```
POST   /api/auth/register
POST   /api/auth/login
```

## User

```
GET    /api/user/me
PUT    /api/user/me
GET    /api/user/{id}
GET    /api/user
PUT    /api/user/{id}
DELETE /api/user/{id}
```

## Category

```
POST   /api/categories
PUT    /api/categories/{id}
GET    /api/categories
GET    /api/categories/{id}
DELETE /api/categories/{id}
```

## Product

```
POST   /api/products
PUT    /api/products/{id}
GET    /api/products
GET    /api/products/{id}
GET    /api/products/search
GET /api/products?page=0&size=10
DELETE /api/products/{id}
```

## Cart

```
POST   /api/cart
GET    /api/cart
PUT    /api/cart/item/{cartItemId}/quantity
DELETE /api/cart/item/{cartItemId}
DELETE /api/cart/clear
```

## Order

```
POST   /api/orders/place
GET    /api/orders/my-orders
GET    /api/orders/{orderId}
PUT    /api/orders/{orderId}/cancel
PUT    /api/orders/{orderId}/status
GET    /api/orders
```

## Payment

```
POST   /api/payments/create
POST   /api/payments/verify
GET    /api/payments/order/{orderId}
```

## Admin

```
GET    /api/admin/dashboard
GET    /api/admin/total-users
GET    /api/admin/total-products
GET    /api/admin/total-orders
GET    /api/admin/total-revenue
GET    /api/admin/recent-orders
GET    /api/admin/top-selling-products
```

## Configure Database

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/foodzy
spring.datasource.username=root
spring.datasource.password=your_password
```
## Razorpay Configuration

- razorpay.key.id=your_razorpay_key_id
- razorpay.key.secret=your_razorpay_key_secret

## Build Project

```bash
mvn clean install
```

## Run Project

```bash
mvn spring-boot:run
```
or simply run the `FoodzyApplication` class.
---

# 📖 Swagger Documentation
```
http://localhost:8080/swagger-ui/index.html
```


# 👨‍💻 Author

**Foodzy Backend Project**

Developed using Java & Spring Boot.