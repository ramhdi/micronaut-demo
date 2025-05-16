CREATE TABLE books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    pages INT NOT NULL,
    CONSTRAINT uk_title_author UNIQUE (title, author)
);

-- Add some sample data
INSERT INTO books (title, author, pages) VALUES ('Dune', 'Frank Herbert', 412);
INSERT INTO books (title, author, pages) VALUES ('Foundation', 'Isaac Asimov', 255);
INSERT INTO books (title, author, pages) VALUES ('1984', 'George Orwell', 328);