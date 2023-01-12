create table members(
    id int primary key auto_increment,
    name varchar(100) not null,
    phone varchar(100) null,
    email varchar(100) null,
    company_id int null,
    active bit
);
alter table members add foreign key (company_id ) references companies(id);