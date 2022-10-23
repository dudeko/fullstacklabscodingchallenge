drop table if exists "MONSTER" CASCADE;


CREATE TABLE "MONSTER" (
    "ID" bigint not null,     
    "ATTACK" bigint not null,
    "DEFENSE" bigint not null,
    "HP" bigint not null,
    "SPEED" bigint not null,
    "IMAGE_URL" varchar(255) not null,
     primary key ("ID")
);