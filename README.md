# E-Commerce Web App

A full-stack Java web application built with **Jakarta Servlets, JSP, Tomcat, Maven, and MySQL**. The app allows users to create an account, sign in, browse products, add items to a session-based cart, and place orders that are saved to a relational database.

This project was built to practice core backend web development concepts such as **MVC structure, session management, form handling, JDBC database access, and order processing** in a traditional Java web application.

## Features

- User registration and login
- Product catalog backed by MySQL
- Session-based shopping cart
- Checkout flow with order + order item persistence
- Order confirmation page
- Seeded sample product data for testing
- MVC-style project structure with Servlets, service classes, models, and JSP views

## Tech Stack

- Java 17
- Maven
- Apache Tomcat 10
- Jakarta Servlets / JSP / JSTL
- MySQL
- JDBC
- HikariCP

## Project Structure

- **Servlets** handle incoming requests and navigation
- **Service classes** handle business logic
- **Models** represent users, products, orders, and cart lines
- **JSP views** render the UI
- **MySQL** stores users, products, orders, and order items
- **HTTP session** stores the active shopping cart

## Prerequisites

Before running the app, make sure you have installed:

- Java 17
- Maven
- MySQL 8+
- Apache Tomcat 10.1+
