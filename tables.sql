-- Active: 1677369082409@@127.0.0.1@3306@library

-- # Trigger

CREATE TABLE 
Books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50),
    author VARCHAR(40),
    isbn INT,
    quantite INT
);

CREATE TABLE
    Clients (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL,
        cin VARCHAR(255) NOT NULL,
        phone VARCHAR(255)
    );

CREATE TABLE
    BorrowedBooks (
        id INT PRIMARY KEY AUTO_INCREMENT,
        book_id INT NOT NULL,
        client_id INT NOT NULL,
        date_borrow_start VARCHAR(20) NOT NULL,
        date_borrow_end VARCHAR(20) NOT NULL,
        price INT,
        FOREIGN KEY (book_id) REFERENCES Books(id),
        FOREIGN KEY (client_id) REFERENCES Clients(id)
    );

CREATE TABLE
    Statuss (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL
    );

CREATE TABLE
    Rapports (
        id INT PRIMARY KEY AUTO_INCREMENT,
        book_id INT,
        status_id INT,
        quantite INT,
        FOREIGN KEY (book_id) REFERENCES Books(id),
        FOREIGN KEY (status_id) REFERENCES Statuss(id)
    );

--Trigger

CREATE TRIGGER  
	BORROWBOOK AFTER
	INSERT
	    on BorrowedBooks for each row
	UPDATE books
	SET
	    `quantite` = books.quantite - 1
	WHERE
	    books.id = NEW.book_id
	    AND books.quantite > 0;


CREATE TRIGGER  
	      RETURNBOOK AFTER
	DELETE
	    ON BorrowedBooks FOR EACH ROW
	UPDATE books
	SET
	    `quantite` = books.quantite + 1
	WHERE books.id = OLD.book_id;


INSERT INTO
    `clients`(`name`, `cin`, `phone`)
VALUES (
        'saad',
        'FB234521',
        '0672533400'
    );

INSERT INTO
    `borrowedbooks` (
        `book_id`,
        `client_id`,
        `date_borrow_start`,
        `date_borrow_end`,
        `price`
    )
VALUES (
        7,
        1,
        CURRENT_DATE,
        CURRENT_DATE + 2,
        20
    );

SELECT * FROM clients;