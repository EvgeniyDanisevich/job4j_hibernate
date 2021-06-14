create table marks (
    id serial primary key,
    name text
);

create table autos (
    id serial primary key,
    name text,
    marks_id int REFERENCES marks (id)
);