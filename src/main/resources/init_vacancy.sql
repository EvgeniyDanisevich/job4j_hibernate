create table if not exists vacancy (
    id serial primary key,
    description varchar(255),
    salary double precision
);