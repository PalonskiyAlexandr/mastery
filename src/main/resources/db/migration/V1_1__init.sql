INSERT INTO author (id,firstName, secondName, birthday, gender) VALUES (1,'Franz', 'Kafka','1883-7-3', 'MALE');

INSERT INTO book (id, name, publisher, year) VALUES (1, 'The metamorphosis', 'Kurt Wolff Verlag', '1915-10-10');

insert into book_author (book_id, author_id) values (1, 1);

INSERT INTO author (id,firstName, secondName, birthday, gender) VALUES (3,'George', 'Orwell','1903-6-25', 'MALE');
SET @person_id1 = LAST_INSERT_ID();

INSERT INTO book (id, name, publisher, year) VALUES (2, '1984', 'Secker and Warburg', '1949-6-8');
SET @book_id1 = LAST_INSERT_ID();

INSERT INTO book (id, name, publisher, year) VALUES (3, 'Animal Farm', 'ACT', '1945-08-17');
SET @book_id2 = LAST_INSERT_ID();

insert into book_author (book_id, author_id) values (2, 3);
insert into book_author (book_id, author_id) values (3, 3);

INSERT INTO author (id,firstName, secondName, birthday, gender) VALUES (4,'Franz', 'Kafka','1883-7-3', 'MALE');