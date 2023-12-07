CREATE table IF NOT EXISTS todo (
    id serial primary key,
    title varchar(255) not null,
    description varchar(255),
    done boolean not null
);