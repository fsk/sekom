-- CREATE DATABASE bank_system;
--
-- \c bank_system;

create table bank
(
    creation_date timestamp(6),
    id            bigserial
        primary key,
    bank_name     varchar(255)
        unique
);

alter table bank
    owner to postgres;

create table bank_account_owner
(
    communication_information_id bigint
        unique,
    creation_date                timestamp(6),
    id                           bigserial
        primary key,
    identity_number              varchar(11)  not null
        unique,
    unique_account_owner_number  uuid,
    first_name                   varchar(255) not null,
    last_name                    varchar(255) not null
);

alter table bank_account_owner
    owner to postgres;

create table bank_account
(
    balance        numeric(38, 2),
    bank_id        bigint
        constraint fkpuk95qcb81gfyqqnj4m7qlqe5
            references bank,
    creation_date  timestamp(6),
    id             bigserial
        primary key,
    owner_id       bigint
        constraint fkeqb9df922iq7p7d48qqitho7r
            references bank_account_owner,
    account_number varchar(22)
        unique
);

alter table bank_account
    owner to postgres;

create table communication_information
(
    bank_account_owner_id bigint
        unique
        constraint fkgoypn70wov5uioykye6lke1tt
            references bank_account_owner,
    id                    bigserial
        primary key,
    phone_number          varchar(13) not null
        unique,
    email                 varchar(255)
);

alter table communication_information
    owner to postgres;

alter table bank_account_owner
    add constraint fktdj5eashtapukpn6nc9h1yh1r
        foreign key (communication_information_id) references communication_information;

create table transaction
(
    amount                numeric(38, 2),
    account_id            bigint
        constraint fk39a303qtsn29mv6kksw98ob2w
            references bank_account,
    bank_account_owner_id bigint
        constraint fkom2dlxa6felv006io6pffnkrt
            references bank_account_owner,
    bank_id               bigint
        constraint fkp5hkbwd41fffytlfetla89j4s
            references bank,
    creation_date         timestamp(6),
    id                    bigserial
        primary key,
    type                  varchar(255)
        constraint transaction_type_check
            check ((type)::text = ANY ((ARRAY ['DEPOSIT'::character varying, 'WITHDRAWAL'::character varying])::text[]))
    );

alter table transaction
    owner to postgres;

