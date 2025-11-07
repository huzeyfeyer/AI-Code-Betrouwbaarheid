CREATE DATABASE security_db;

USE security_db;

CREATE TABLE passwords (
  id INT AUTO_INCREMENT PRIMARY KEY,
  hash VARCHAR(256),
  salt VARCHAR(32)
);

