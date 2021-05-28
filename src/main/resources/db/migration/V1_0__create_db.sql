CREATE TABLE IF NOT EXISTS author (
       id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
        birthday date not null,
        firstName varchar(20) not null,
        gender varchar(10),
        secondName varchar(20) not null,
    );
CREATE TABLE IF NOT EXISTS book (
       id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name varchar(20) not null,
        publisher varchar(30) not null,
        year date not null,
    );
CREATE TABLE IF NOT EXISTS book_author (
           book_id bigint references book(id),
           author_id bigint references author(id),
           PRIMARY KEY(book_id, author_id)
        )




