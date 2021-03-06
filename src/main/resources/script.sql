INSERT INTO Author (id,firstName, secondName, birthday, sex) VALUES (1,'Аркадий', 'Стругацкий','1925-8-28', 'MALE');
SET @person_id1 = LAST_INSERT_ID();

INSERT INTO Author (id,firstName, secondName, birthday, sex) VALUES (2,'Борис', 'Стругацкий','1933-4-15', 'MALE');
SET @person_id2 = LAST_INSERT_ID();

INSERT INTO Book (id, name, publisher, year) VALUES (1, 'Пикник на обочине', 'Молодая гвардия', '1972-10-10');
SET @book_id1 = LAST_INSERT_ID();

insert into BookAuthor (book_id, author_id) values (@book_id1,@person_id1)
insert into BookAuthor (book_id, author_id) values (@book_id1,@person_id2)

INSERT INTO Author (id,firstName, secondName, birthday, sex) VALUES (3,'Джордж', 'Оруэлл','1903-6-25', 'MALE');
SET @person_id1 = LAST_INSERT_ID();

INSERT INTO Book (id, name, publisher, year) VALUES (2, '1984', 'Secker and Warburg', '1949-6-8');
SET @book_id1 = LAST_INSERT_ID();

INSERT INTO Book (id, name, publisher, year) VALUES (3, 'Скотный двор', 'АСТ', '1945-08-17');
SET @book_id2 = LAST_INSERT_ID();

insert into BookAuthor (book_id, author_id) values (@book_id1,@person_id1)
insert into BookAuthor (book_id, author_id) values (@book_id2,@person_id1)

INSERT INTO Author (id,firstName, secondName, birthday, sex) VALUES (4,'Франц', 'Кафка','1883-7-3', 'MALE');
