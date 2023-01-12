create table agents(
    id int primary key auto_increment,
    name varchar(100) not null,
    username varchar(100) not null,
    keycloak_id varchar(100) not null
);