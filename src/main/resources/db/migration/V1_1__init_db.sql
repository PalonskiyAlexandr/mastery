INSERT INTO author (firstName, secondName, birthday, gender) VALUES ('Franz', 'Kafka','1883-7-3', 'MALE');

INSERT INTO book (name, publisher, year) VALUES ('The metamorphosis', 'Kurt Wolff Verlag', '1915-10-10');

insert into book_author (book_id, author_id) values (1, 1);

INSERT INTO author (firstName, secondName, birthday, gender) VALUES ('George', 'Orwell','1903-6-25', 'MALE');

INSERT INTO book (name, publisher, year) VALUES ('1984', 'Secker and Warburg', '1949-6-8');

INSERT INTO book (name, publisher, year) VALUES ('Animal Farm', 'ACT', '1945-08-17');

insert into book_author (book_id, author_id) values (2, 2);
insert into book_author (book_id, author_id) values (3, 2);

INSERT INTO author (firstName, secondName, birthday, gender) VALUES ('Arkady', 'Strugatsky','1925-8-28', 'MALE');

INSERT INTO author (firstName, secondName, birthday, gender) VALUES ('Boris ', 'Strugatsky','1933-4-15', 'MALE');

INSERT INTO book (name, publisher, year) VALUES ('Roadside Picnic', 'Macmillan', '1972-10-10');

insert into book_author (book_id, author_id) values (4,3);
insert into book_author (book_id, author_id) values (4,4);
