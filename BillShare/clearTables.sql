create database if not exists BillShare;

use BillShare;

DROP TABLE clear_items;
DROP TABLE clear_info;

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

);


create user 'admin'@'localhost' identified by '123456';

grant all privileges on billshare.* to 'admin'@'localhost';

flush privileges;