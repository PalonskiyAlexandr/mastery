CREATE TABLE IF NOT EXISTS users(
        id serial PRIMARY KEY,
        login varchar(50) not null,
        name varchar(50) not null,
        surname varchar(50) not null,
        password varchar(50) not null
    );
CREATE TABLE IF NOT EXISTS roles(
        id serial PRIMARY KEY,
        name varchar(50) not null
    );
CREATE TABLE IF NOT EXISTS users_roles(
       user_id bigint references users(id),
       role_id bigint references roles(id),
       PRIMARY KEY(user_id, role_id)
    );



