CREATE TABLE IF NOT EXISTS products(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
category VARCHAR(100),
price DECIMAL(10,2) NOT NULL
);


CREATE TABLE IF NOT EXISTS users (
id INT AUTO_INCREMENT PRIMARY KEY,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
full_name VARCHAR(255),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

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