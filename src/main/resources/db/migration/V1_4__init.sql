INSERT INTO users (login, name, surname, password) VALUES ('admin', 'Василий','Васильев', '4y0h9WnLw/TjWXpwK9EZ4D7WCZaB9s/2U/sPcnup1do=');

INSERT INTO roles (name) VALUES ('ADMIN');

insert into users_roles (user_id, role_id) values (1, 1);

INSERT INTO users (login, name, surname, password) VALUES ('user', 'Виктор','Викторъев', 'UmgoIhfJWomN4jLW5wPXoMkIY8l5PPY6Tq8bPjzx6mg=');

INSERT INTO roles (name) VALUES ('USER');

insert into users_roles (user_id, role_id) values (2, 2);