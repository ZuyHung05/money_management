# 📚 Household Expense Management

This project is a system for managing expenses and sharing bills among groups of users living in the same house.  
It is built with **Java Spring Boot** and **MySQL**, supporting expense tracking, cost splitting, member management, payment groups, and transparent, convenient notifications.

---

## 🚀 Key Features
- 👤 **User Management**: Register, log in, update profile, manage device tokens.
- 🏠 **House Management**: Create, join, leave houses, update house name, check members.
- 💸 **Expense Management**: Create, view, delete expenses, track costs, share bills.
- 👥 **Payment Group Management**: Create, validate, delete groups, view groups by house or member.
- 🔔 **Notification System**: Send, read, track unread notifications.
- 📊 **Expense Statistics**: Total cost, paid amount, outstanding balance by month, member-based statistics.

---

## 🛠 Technologies Used
- **Backend**: Java 17, Spring Boot, Hibernate, JPA
- **Database**: MySQL
- **Others**: Maven, Lombok

---

## ⚙️ Installation

### 1. Clone the repository
```bash
git clone https://github.com/ZuyHung05/money_management.git
cd money_management
### 2. Configure the database
Create a MySQL database:
```bash
CREATE DATABASE money_management;
Update the database connection in src/main/resources/application.properties:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/money_management
spring.datasource.username=root
spring.datasource.password=your_password

