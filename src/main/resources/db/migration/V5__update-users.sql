DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id serial,
    username varchar(30),
    password varchar(100),
    name varchar(30),
    email varchar(30),
    UNIQUE (username),
    PRIMARY KEY (id)
);

INSERT INTO users (username, password, name, email) VALUES ('user1', '$2a$10$YSKkkJ3qc1ML8psEZc.o5.xonuOhCBXui.hCWiq7qh4Ucx4W6Once', 'Ivan', 'ivan@mail.ru'),
('user2', '$2a$10$YSKkkJ3qc1ML8psEZc.o5.xonuOhCBXui.hCWiq7qh4Ucx4W6Once', 'Sergei', 'sergei@mail.ru');

CREATE TABLE roles (
    id serial,
    name varchar(30),
    PRIMARY KEY (id)
);

INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');



CREATE TABLE users_roles (
    user_id int NOT NULL,
    role_id int NOT NULL,
    PRIMARY KEY(user_id, role_id),
    CONSTRAINT userFK FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT roleFK FOREIGN KEY (role_id) REFERENCES roles(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO users_roles VALUES (1, 1), (1, 2), (2, 1);