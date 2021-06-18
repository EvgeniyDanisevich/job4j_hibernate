create table if not exists candidate (
    id serial primary key,
    name varchar(255),
    experience varchar(255),
    salary double precision
);