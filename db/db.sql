CREATE DATABASE room_reservation_db;

USE room_reservation_db;

-- 1) USERS TABLE
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  email VARCHAR(150) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(30) NOT NULL DEFAULT 'STAFF',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2) ROOM TYPES TABLE
CREATE TABLE IF NOT EXISTS room_types (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  rate_per_night DECIMAL(10,2) NOT NULL,
  capacity INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 3) RESERVATIONS TABLE
CREATE TABLE IF NOT EXISTS reservations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  reservation_no VARCHAR(30) NOT NULL UNIQUE,

  guest_name VARCHAR(120) NOT NULL,
  guest_address VARCHAR(255) NOT NULL,
  contact_no VARCHAR(30) NOT NULL,

  room_type_id BIGINT NOT NULL,

  check_in DATE NOT NULL,
  check_out DATE NOT NULL,

  total_nights INT NOT NULL,
  total_amount DECIMAL(10,2) NOT NULL,

  status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_reservation_roomtype
    FOREIGN KEY (room_type_id) REFERENCES room_types(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

  CONSTRAINT chk_dates CHECK (check_out > check_in),
  CONSTRAINT chk_status CHECK (status IN ('ACTIVE', 'CANCELLED'))
);

INSERT INTO room_types (name, rate_per_night, capacity) VALUES
('Single', 5000.00, 1),
('Double', 8000.00, 2),
('Deluxe', 12000.00, 3),
('Suite', 20000.00, 4)
ON DUPLICATE KEY UPDATE
rate_per_night = VALUES(rate_per_night),
capacity = VALUES(capacity);


SHOW TABLES;

SELECT * FROM room_types;
SELECT * FROM users;

DESCRIBE reservations;

USE room_reservation_db;

INSERT INTO users (username, email, password_hash, role)
VALUES ('admin', 'admin@oceanview.com', '$2a$10$BHswi07aPY3EfBuUeDjhEeg9MVMIqrjjdvS8nhwVi3ZJ18ck9QokK', 'ADMIN');

ALTER TABLE reservations
MODIFY reservation_no VARCHAR(30) NULL;