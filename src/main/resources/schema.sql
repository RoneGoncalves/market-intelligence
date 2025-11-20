CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    external_id BIGINT,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    age INT,
    gender VARCHAR(20)
);
