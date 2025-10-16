-- DROP in dependency order if the DB exists
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;

-- PRODUCTS
CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  category VARCHAR(100),
  price DECIMAL(10,2) NOT NULL
) ENGINE=InnoDB;

-- USERS
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ORDERS (user_id matches users.id type: INT)
CREATE TABLE orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_orders_user
    FOREIGN KEY (user_id) REFERENCES users(id)
      ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ORDER ITEMS
CREATE TABLE order_items (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT NOT NULL,
  product_id INT NOT NULL,
  qty INT NOT NULL,
  price_each DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_items_order
    FOREIGN KEY (order_id) REFERENCES orders(id)
      ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_items_product
    FOREIGN KEY (product_id) REFERENCES products(id)
      ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- SEED PRODUCTS
INSERT INTO products (id, name, category, price) VALUES
  (1,'Hardwood Oak Suffolk Internal Door','Doors',109.99),
  (2,'Oregon Cottage Interior Oak Door','Doors',179.99),
  (3,'Oregon Cottage Horizontal White Oak Door','Doors',189.99),
  (4,'4 Panel Oak Deco Interior Door','Doors',209.09),
  (5,'Worcester 2000 30kW Combi Boiler (Comfort+ II)','Boilers',989.99),
  (6,'Glow-worm Betacom 4 30kW Combi Gas Boiler ERP','Boilers',787.99),
  (7,'Worcester 2000 25kW Combi Boiler (Comfort+ II)','Boilers',859.99)
ON DUPLICATE KEY UPDATE
  name=VALUES(name), category=VALUES(category), price=VALUES(price);
