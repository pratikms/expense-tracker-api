DROP DATABASE expensetrackerdb;

DROP USER expensetracker;


CREATE USER expensetracker WITH PASSWORD 'password';

CREATE DATABASE expensetrackerdb WITH TEMPLATE=template0 OWNER=expensetracker;


\connect expensetrackerdb;

ALTER DEFAULT PRIVILEGES GRANT ALL ON TABLES TO expensetracker;

ALTER DEFAULT PRIVILEGES GRANT ALL ON SEQUENCES TO expensetracker;


CREATE TABLE users(
    id INTEGER PRIMARY KEY NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE categories(
    id INTEGER PRIMARY KEY NOT NULL,
    user_id INTEGER NOT NULL,
    title VARCHAR(20) NOT NULL,
    description VARCHAR(50) NOT NULL
);

ALTER TABLE categories ADD CONSTRAINT cat_user_fk FOREIGN KEY (user_id) REFERENCES users(id);

CREATE TABLE transactions(
    id INTEGER PRIMARY KEY NOT NULL,
    category_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    note VARCHAR(50) NOT NULL,
    transaction_date BIGINT NOT NULL
);

ALTER TABLE transactions ADD CONSTRAINT trans_cat_fk FOREIGN KEY (category_id) REFERENCES categories(id);

ALTER TABLE transactions ADD CONSTRAINT trans_user_fk FOREIGN KEY (user_id) REFERENCES users(id);

CREATE SEQUENCE users_seq INCREMENT 1 START 1;

CREATE SEQUENCE categories_seq INCREMENT 1 START 1;

CREATE SEQUENCE transactions_seq INCREMENT 1 START 1000;