INSERT INTO users (login, name, surname, password) VALUES ('admin', 'Василий','Васильев', '$2a$10$vwS77pJXD0Q48d19I.cNSe6xh80.sxowizVMcYm2sEV4APNcVPCFO');

INSERT INTO role (name) VALUES ('ADMIN');

insert into users_role (user_id, role_id) values (1, 1);

INSERT INTO users (login, name, surname, password) VALUES ('user', 'Виктор','Викторъев', '$2a$10$pg7nh7ey5rN4VHqe7cWF5OzCFrrRP.SsY7v9cHiw8KSHOxMvJulu2');

INSERT INTO role (name) VALUES ('USER');

insert into users_role (user_id, role_id) values (2, 2);