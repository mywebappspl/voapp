create table companies(
    id int primary key auto_increment,
    company_name varchar(100) not null,
    phone varchar(100) null,
    email varchar(100) null,
    active bit
);