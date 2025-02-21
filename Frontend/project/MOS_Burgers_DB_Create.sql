DROP DATABASE IF EXISTS mos_burgers;
CREATE DATABASE mos_burgers;
USE mos_burgers;

CREATE TABLE report (
	report_id INT AUTO_INCREMENT,
	creation_date DATE NOT NULL,
	type ENUM('Monthly', 'Annual') NOT NULL,
	detail VARCHAR(256),
	PRIMARY KEY (report_id)
);

CREATE TABLE food_item (
	item_id INT AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
	code VARCHAR(15) NOT NULL UNIQUE,
	price DOUBLE(8, 2) NOT NULL,
	discount DOUBLE(8, 2) DEFAULT 0.0,
	category ENUM('Burgers', 'Submarines', 'Beverages', 'Other'),
	PRIMARY KEY (item_id)
);

CREATE TABLE `admin` (
	admin_id INT AUTO_INCREMENT,
	name VARCHAR(30),
	phone VARCHAR(12) UNIQUE,
	email VARCHAR(30),
	address VARCHAR(60),
	salary DOUBLE(8, 2),
	position ENUM('Cashier', 'Manager'),
	dob DATE,
	password VARCHAR(255),
	PRIMARY KEY (admin_id)
);

CREATE TABLE customer (
	customer_id INT AUTO_INCREMENT,
	name VARCHAR(30),
	phone VARCHAR(12),
	email VARCHAR(30),
	address VARCHAR(60),
	PRIMARY KEY (customer_id)
);

CREATE TABLE mos_order (
	order_id INT AUTO_INCREMENT,
	place_date DATE NOT NULL,
	total_amount DOUBLE(9, 2) NOT NULL,
	discount DOUBLE(7, 2) NOT NULL DEFAULT 0.0,
	final_amount DOUBLE(9, 2) NOT NULL,
	customer_id INT,
	admin_id INT,
	FOREIGN KEY (admin_id) REFERENCES admin (admin_id),
	FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
	PRIMARY KEY (order_id)
);

CREATE TABLE order_item (
	item_id INT,
	order_id INT,
	quantity INT(5) NOT NULL,
	total_price DOUBLE(8, 2) NOT NULL,
	price_per_unit DOUBLE(7, 2) NOT NULL,
	FOREIGN KEY (item_id) REFERENCES food_item (item_id),
	FOREIGN KEY (order_id) REFERENCES mos_order (order_id),
	PRIMARY KEY (item_id, order_id)
);

CREATE TABLE receipt (
	receipt_id INT AUTO_INCREMENT,
	receipt_date DATE NOT NULL,
	receipt_time TIME NOT NULL,
	order_id INT,
	FOREIGN KEY (order_id) REFERENCES mos_order (order_id),
	PRIMARY KEY (receipt_id)
);

DESC report;
DESC food_item;
DESC `admin`;
DESC customer;
DESC mos_order;
DESC order_item;
DESC receipt;

INSERT INTO report (creation_date, type, detail) VALUES
('2024-01-01', 'Annual', 'Annual performance and sales report.'),
('2024-03-01', 'Monthly', 'Sales analysis for February.'),
('2024-04-01', 'Monthly', 'Customer feedback summary.'),
('2024-05-01', 'Monthly', 'Inventory management insights.'),
('2023-12-01', 'Annual', 'Yearly report of expenses and revenue.'),
('2024-06-01', 'Monthly', 'Promotional campaign results.'),
('2023-11-01', 'Monthly', 'Operational challenges summary.'),
('2024-02-01', 'Monthly', 'Special offers impact review.'),
('2023-10-01', 'Monthly', 'Staff performance evaluation.'),
('2023-09-01', 'Monthly', 'Quarterly targets overview.');

INSERT INTO food_item (name, code, price, discount, category) VALUES
('Classic Burger', 'CLB001', 5.99, 0.50, 'Burgers'),
('Cheese Burger', 'CHB002', 6.49, 0.30, 'Burgers'),
('Veggie Burger', 'VGB003', 5.49, 0.00, 'Burgers'),
('Chicken Submarine', 'CHS004', 7.99, 1.00, 'Submarines'),
('Beef Submarine', 'BFS005', 8.49, 0.50, 'Submarines'),
('Coke', 'CK006', 1.99, 0.20, 'Beverages'),
('Orange Juice', 'OJ007', 2.49, 0.00, 'Beverages'),
('Fries', 'FR008', 2.99, 0.00, 'Other'),
('Ice Cream', 'IC009', 3.99, 0.25, 'Other'),
('Milkshake', 'MS010', 4.49, 0.50, 'Beverages');

INSERT INTO `admin` (name, phone, email, address, salary, position, dob, password) VALUES
('GodXero', '0770110422', 'godxero@heaven.com', '123 Burger St.', 300000.00, 'Manager', '2001-07-25', '1234'),
('John Doe', '1234567890', 'john.doe@mos.com', '123 Burger St.', 3000.00, 'Manager', '1985-03-25', 'manager123'),
('Jane Smith', '9876543210', 'jane.smith@mos.com', '456 Submarine Ave.', 2500.00, 'Cashier', '1990-07-15', 'cashier456'),
('Mark Lee', '1122334455', 'mark.lee@mos.com', '789 Fries Ln.', 3200.00, 'Manager', '1982-11-05', 'admin789'),
('Emily Clark', '2233445566', 'emily.clark@mos.com', '101 Dessert Rd.', 2800.00, 'Cashier', '1995-05-20', 'clerk888'),
('Alice Brown', '3344556677', 'alice.brown@mos.com', '202 Shake Blvd.', 2900.00, 'Manager', '1988-09-30', 'shake999');

INSERT INTO customer (name, phone, email, address) VALUES
('Tom Hanks', '5551234567', 'tom.hanks@gmail.com', '25 Hollywood St.'),
('Sarah Connor', '5557654321', 'sarah.connor@gmail.com', '42 Sci-Fi Blvd.'),
('Jack Dawson', '5556789012', 'jack.dawson@gmail.com', '15 Titanic Ln.'),
('Rose DeWitt', '5558901234', 'rose.dewitt@gmail.com', '23 Drama Ave.'),
('Clark Kent', '5552468101', 'clark.kent@gmail.com', '77 Hero Rd.');

SELECT * FROM report;
SELECT * FROM food_item;
SELECT * FROM `admin`;
SELECT * FROM customer;
