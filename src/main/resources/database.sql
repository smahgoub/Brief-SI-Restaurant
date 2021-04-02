create table if not exists tables
(
    id serial not null
    constraint table_pk
    primary key,
    nb_de_personne integer,
    num_table integer
);

alter table tables owner to postgres;

create table if not exists serveurs
(
    id serial not null
    constraint server_pk
    primary key,
    prenom varchar,
    nom varchar
);

alter table serveurs owner to postgres;

create table if not exists plat
(
    id serial not null
    constraint dish_pk
    primary key,
    nom varchar,
    px_unitaire double precision
);

alter table plat owner to postgres;

create table if not exists facture
(
    id serial not null
    constraint bill_pk
    primary key,
    serveurs_idx integer
    constraint "Server_fk"
    references serveurs,
    tables_idx integer
    constraint "Table_fk"
    references tables
);

alter table facture owner to postgres;

create table if not exists facture_ligne
(
    facture_idx integer
    constraint "Bill_idx_fk"
    references facture,
    plat_idx integer
    constraint "Dish_fk"
    references plat,
    nb_plat integer
);

alter table facture_ligne owner to postgres;

