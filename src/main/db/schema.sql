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


insert into rules values (1, 'Статья. 1');
insert into rules values (2, 'Статья. 2');
insert into rules values (3, 'Статья. 3');

insert into types values (1, 'Две машины');
insert into types values (2, 'Машина и человек');
insert into types values (3, 'Машина и велосипед');
