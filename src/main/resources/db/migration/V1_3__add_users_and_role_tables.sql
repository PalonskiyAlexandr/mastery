CREATE TABLE IF NOT EXISTS users(
       id serial PRIMARY KEY,
        login varchar(50) not null,
        name varchar(50) not null,
        surname varchar(50) not null,
        password varchar(100) not null,
        email varchar(70) not null,
        enabled boolean not null
    );
CREATE TABLE IF NOT EXISTS role(
       id serial PRIMARY KEY,
        name varchar(50) not null
    );
CREATE TABLE IF NOT EXISTS users_role(
       user_id bigint references users(id),
       role_id bigint references role(id),
       PRIMARY KEY(user_id, role_id)
    );



