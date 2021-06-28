CREATE TABLE IF NOT EXISTS verification_token(
       id serial PRIMARY KEY,
        token varchar(50) not null,
        createdAt TIMESTAMP,
        expiredAt TIMESTAMP,
        confirmedAt TIMESTAMP,
        user_id bigint not null references users(id)
    );