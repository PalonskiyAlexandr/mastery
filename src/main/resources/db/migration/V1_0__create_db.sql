CREATE TABLE IF NOT EXISTS author (
       id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
        birthday date,
        firstName varchar(50) not null,
        gender varchar(20),
        secondName varchar(50) not null,
    );
CREATE TABLE IF NOT EXISTS book (
       id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name varchar(50) not null,
        publisher varchar(50) not null,
        year date,
    );
CREATE TABLE IF NOT EXISTS book_author (
           book_id bigint references book(id),
           author_id bigint references author(id),
           PRIMARY KEY(book_id, author_id)
        )




