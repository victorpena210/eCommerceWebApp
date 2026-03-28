E-Commerce Web App

A full-stack Java web application built with Jakarta Servlets, JSP, Tomcat, Maven, and MySQL. The app allows users to create an account, sign in, browse products, add items to a session-based cart, and place orders that are saved to a relational database.

This project was built to practice core backend web development concepts such as MVC structure, session management, form handling, JDBC database access, and order processing in a traditional Java web application.

Features
User registration and login
Product catalog backed by MySQL
Session-based shopping cart
Checkout flow with order + order item persistence
Order confirmation page
Seeded sample product data for testing
MVC-style project structure with Servlets, service classes, models, and JSP views
Tech Stack
Java 17
Maven
Apache Tomcat 10
Jakarta Servlets / JSP / JSTL
MySQL
JDBC
HikariCP
Project Structure
Servlets handle incoming requests and navigation
Service classes handle business logic
Models represent users, products, orders, and cart lines
JSP views render the UI
MySQL stores users, products, orders, and order items
HTTP session stores the active shopping cart
Prerequisites

Before running the app, make sure you have installed:

Java 17
Maven
MySQL 8+
Apache Tomcat 10.1+
How to Download and Run the App
1. Clone the repository
git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
cd YOUR_REPO_NAME
2. Create the MySQL database

Open MySQL and create the database:

CREATE DATABASE ecommerce;

Then run the schema file:

mysql -u root -p ecommerce < db/schema.sql

This will create the tables and insert sample products.

3. Configure the database connection

This app uses a Tomcat JNDI DataSource, so the main database connection is configured in:

src/main/webapp/META-INF/context.xml

Update the username and password there to match your MySQL setup:

<Context>
  <Resource name="jdbc/app"
            auth="Container"
            type="javax.sql.DataSource"
            username="root"
            password="your_mysql_password"
            driverClassName="com.mysql.cj.jdbc.Driver"
            url="jdbc:mysql://localhost:3306/ecommerce?serverTimezone=UTC"
            maxTotal="20"
            maxIdle="10"
            maxWaitMillis="10000"/>
</Context>
4. Build the project
mvn clean package

This generates a WAR file in the target folder.

5. Deploy to Tomcat

Copy the generated WAR file into Tomcat’s webapps folder.

Example WAR file:

target/ecommerce-app.war

After Tomcat starts, open:

http://localhost:8080/ecommerce-app
Running in Eclipse

If you are using Eclipse:

Import the project as an Existing Maven Project
Add your Tomcat 10 runtime
Make sure the project is associated with Tomcat
Confirm MySQL is running and the ecommerce database exists
Run the project on the server

Then open:

http://localhost:8080/ecommerce-app
How to Test the App

Use this flow to test the main features:

Account flow
Go to /signup
Create a new account
Log in through /login
Shopping flow
Open /catalog
Add products to the cart
Open /cart
Verify totals and quantities
Checkout flow
Click Checkout
Confirm you are redirected to the order confirmation page
Verify that the order is saved in the database
Example Test Checklist
Register a new user
Log in successfully
Browse the product catalog
Add multiple items to cart
Verify cart total is correct
Complete checkout
Confirm rows were inserted into:
orders
order_items
Notes
The cart is stored in the user session, not in the database
Orders are persisted to MySQL during checkout
Sample products are seeded for local testing
This project focuses on learning traditional Java web app architecture using Servlets and JSP
Future Improvements
Password hashing with BCrypt
Better validation and error handling
Product detail pages
Quantity update and remove-from-cart actions
Admin/product management
Improved frontend styling
