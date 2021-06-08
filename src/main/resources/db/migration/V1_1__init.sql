INSERT INTO author (id,firstName, secondName, birthday, gender) VALUES (1,'Franz', 'Kafka','1883-7-3', 'MALE');

INSERT INTO book (id, name, publisher, year) VALUES (1, 'The metamorphosis', 'Kurt Wolff Verlag', '1915-10-10');

insert into book_author (book_id, author_id) values (1, 1);

INSERT INTO author (id,firstName, secondName, birthday, gender) VALUES (2,'George', 'Orwell','1903-6-25', 'MALE');

INSERT INTO book (id, name, publisher, year) VALUES (2, '1984', 'Secker and Warburg', '1949-6-8');

INSERT INTO book (id, name, publisher, year) VALUES (3, 'Animal Farm', 'ACT', '1945-08-17');

insert into book_author (book_id, author_id) values (2, 2);
insert into book_author (book_id, author_id) values (3, 2);

INSERT INTO author (id,firstName, secondName, birthday, gender) VALUES (3,'Arkady', 'Strugatsky','1925-8-28', 'MALE');

INSERT INTO author (id,firstName, secondName, birthday, gender) VALUES (4,'Boris ', 'Strugatsky','1933-4-15', 'MALE');

INSERT INTO book (id, name, publisher, year) VALUES (4, 'Roadside Picnic', 'Macmillan', '1972-10-10');

insert into book_author (book_id, author_id) values (4,3);
insert into book_author (book_id, author_id) values (4,4);
