create database workshop2
    character set utf8mb4
    collate utf8mb4_unicode_ci;

use workshop2;

create table users (
    id int auto_increment primary key not null,
    email varchar(255) unique not null,
    username varchar(255) not null,
    password varchar(60) not null
);

describe users;