CREATE TABLE accident (
    id serial primary key,
    name varchar(2000),
    text text,
    address text,
    type int,
    rules character varying[]
);

CREATE TABLE rules (
    id serial primary key,
    name varchar(2000)
);

CREATE TABLE types (
    id serial primary key,
    name varchar(2000)
);