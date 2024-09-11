CREATE TABLE authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    bio VARCHAR
);

CREATE TABLE genres (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    author_id INT NOT NULL,
    genre_id INT NOT NULL,
    published_date DATE NOT NULL,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors (id),
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genres (id)
);

INSERT INTO authors (name, bio)
VALUES
('J.K. Rowling', 'British author'),
('George Orwell', 'English ');

INSERT INTO genres (name)
VALUES
('Fantasy'),
('Dystopian'),
('Science Fiction');

INSERT INTO books (title, isbn, author_id, genre_id, published_date)
VALUES
('Harry Potter and the Philosopher''s Stone', '111', 1, 1, '2024-06-26'),
('1984', '112', 2, 2, '2023-06-08');

