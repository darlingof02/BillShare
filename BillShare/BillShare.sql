create database if not exists BillShare;

use BillShare;

DROP TABLE clear_items;
DROP TABLE clear_info;
DROP TABLE indebt;
DROP TABLE own;
DROP TABLE bill;
DROP TABLE user;


create table user (
    uid             Integer,
    lastname        varchar(30) not null,
    firstname       varchar(30) not null,
    nickname        varchar(30),
    password        varchar(60) not null,
    email           varchar(30) not null,
    avatar          text,
    tel             long,
    primary key (uid)
);

create table bill (
    bid             Integer,
    oid             Integer not null,
    amount          Integer not null,
    receipt         text,
    status          Integer not null,
    create_time     timestamp,
    finish_time     timestamp,
    type            varchar(30),
    comment         text,
    primary key (bid),
    foreign key (oid) references user(uid) on delete cascade
);

create table own (
    uid             Integer,
    bid             Integer,
    primary key(bid),
    foreign key (uid) references user(uid) on delete cascade,
    foreign key (bid) references bill(bid) on delete cascade
);

create table indebt (
    did             Integer,
    bid             Integer,
    status          Integer not null,
    accept_time     timestamp,
    pay_time        timestamp,
    amount          Integer not null,
    primary key(did, bid),
    foreign key (did) references user(uid) on delete cascade,
    foreign key (bid) references bill(bid) on delete cascade
);

create table clear_info (
    cid             Integer,
    uid1            Integer not null,
    uid2            Integer not null,
    total_amount    Integer not null,
    status          Integer not null,
    create_Time     timestamp,
    clear_Time      timestamp,

    primary key (cid),
    foreign key (uid1) references user(uid) on delete cascade,
    foreign key (uid2) references user(uid) on delete cascade
);

create table clear_items (
    cid             Integer,
    bid             Integer,
    did             Integer,
    primary key (cid, bid),
    foreign key (cid) references clear_info(cid) on delete cascade,
    foreign key (bid) references bill(bid) on delete cascade,
    foreign key (did) references user(uid) on delete cascade

);



create user 'admin'@'localhost' identified by '123456';

grant all privileges on billshare.* to 'admin'@'localhost';

flush privileges;