create table driver (
    id serial primary key,
    name text
);

create table engine (
    id serial primary key,
    name text
);

create table auto (
    id serial primary key,
    engine_id int not null references engine(id)
);

create table history_owner (
    id serial primary key,
    driver_id int not null references driver(id),
    auto_id int not null references auto(id)
);