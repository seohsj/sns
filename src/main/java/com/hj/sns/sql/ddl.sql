create table user
(
    id       bigint generated by default as identity,
    username varchar(255) not null,
    password varchar(255) not null,
    primary key (id),
    unique (username)
);

create table photo
(
    id                 bigint generated by default as identity,
    user_id            bigint,
    content            clob,
    created_date       timestamp,
    image_path         clob,
    last_modified_date timestamp,
    primary key (id),
    foreign key (user_id) references user (id)
);



create table comment
(
    id      bigint generated by default as identity,
    content clob,
    user_id bigint,
    primary key (id),
    foreign key (user_id) references user (id),
    foreign key (photo_id) references photo (id)
);

create table follow
(
    id      bigint generated by default as identity,
    who_id  bigint,
    whom_id bigint,
    primary key (id)
        foreign key (who_id),
    references user (id),
    foreign key (whom_id) references user (id)
);
create table likes
(
    id       bigint generated by default as identity,
    photo_id bigint,
    user_id  bigint,
    primary key (id),
    foreign key (photo_id) references photo (id),
    foreign key (user_id) references user (id)
);
create table tag
(
    id  bigint generated by default as identity,
    tag varchar(255),
    primary key (id)
);
create table photo_tag
(
    id       bigint generated by default as identity,
    photo_id bigint,
    tag_id   bigint,
    primary key (id),
    foreign key (photo_id) references photo (id),
    foreign key (tag_id) references tag (id)
);
