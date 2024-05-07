create table zipcode
(
    zipcode_id int not null
        primary key,
    zipcode    int not null
);

create table charging_station
(
    id        int    not null
        primary key,
    latitude  decimal(10, 6) not null,
    longitude decimal(10, 6) not null,
    zipcode   int            not null,
    constraint charging_station_pk_2
        unique (id),
    constraint charging_station_zipcode_zipcode_id_fk
        foreign key (zipcode) references zipcode (zipcode_id)
);

