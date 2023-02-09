create table if not exists card.users
(
    id       int auto_increment
        primary key,
    login    varchar(100) not null,
    password varchar(100) not null,
    name     varchar(100) not null,
    regDate  datetime     not null,
    constraint login
        unique (login)
);

create table if not exists card.categories
(
    id     int auto_increment
        primary key,
    name   varchar(100) not null,
    userId int          not null,
    constraint name
        unique (name),
    constraint categories_ibfk_1
        foreign key (userId) references card.users (id)
            on update cascade on delete cascade
);

create index userId
    on card.categories (userId);

create table if not exists card.cards
(
    id           int auto_increment
        primary key,
    question     varchar(140) not null,
    answer       varchar(140) not null,
    categoryId   int          not null,
    creationDate datetime     not null,
    constraint cards_ibfk_1
        foreign key (categoryId) references card.categories (id)
            on update cascade on delete cascade
);

create index categoryId
    on card.cards (categoryId);
