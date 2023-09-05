-- Active: 1675161931111@@127.0.0.1@3306@library

-- # Trigger

CREATE TABLE 

Books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50),
    author VARCHAR(40),
    isbn VARCHAR(100),
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
        book_id INT,
        client_id INT,
        date_borrow_start DATETIME,
        date_borrow_end DATETIME,
        price INT,
        FOREIGN KEY (book_id) REFERENCES Books(id),
        FOREIGN KEY (client_id) REFERENCES Clients(id)
    );

CREATE TABLE
    Statues (
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
        FOREIGN KEY (status_id) REFERENCES Statues(id)
    );

--Trigger

CREATE TRIGGER BORROWBOOK 
	  AFTER
	INSERT
	    on BorrowedBooks for each row
	UPDATE books
	SET
	    `quantite` = books.quantite - 1
	WHERE
	    books.id = NEW.book_id
	    AND books.quantite > 0;


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
VALUES(7, 1, CURRENT_DATE , CURRENT_DATE + 2 , 20);

SELECT * FROM clients;