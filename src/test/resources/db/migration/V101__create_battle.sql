drop table if exists "BATTLE" CASCADE;

create table  "BATTLE" (
    "ID" bigint not null, 
    "MONSTER_A_ID" bigint not null, 
    "MONSTER_B_ID" bigint not null, 
    "MONSTER_WINNER" bigint not null,     
     primary key ("ID")
);