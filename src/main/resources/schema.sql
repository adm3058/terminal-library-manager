CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publish_year INT,
    isbn VARCHAR(50),
    available BOOLEAN
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS loans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    book_id BIGINT,
    loan_date DATE,
    return_date DATE
);

INSERT INTO users (username, password, role)
SELECT 'user', '$2a$10$2qNUlyYh7qVc6qFRtNPX/u7OrxDQPbMuMzuexJAjLJb4qQCT2W79O', 'USER'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'user'
);

INSERT INTO users (username, password, role)
SELECT 'admin', '$2a$10$s5yfst2L7Yi9Fd..g7rXF.U7/Xbm8cJg1Hq/K22xLNg4N6mv5mBku', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'admin'
);

INSERT INTO books (title, author, publish_year, isbn, available)
SELECT 'Lalka', 'Bolesław Prus', 1890, '123456', TRUE
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'Lalka');

INSERT INTO books (title, author, publish_year, isbn, available)
SELECT 'Wesele', 'Stanisław Wyspiański', 1901, '234567', TRUE
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'Wesele');

INSERT INTO books (title, author, publish_year, isbn, available)
SELECT '1984', 'George Orwell', 1949, '345678', TRUE
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = '1984');