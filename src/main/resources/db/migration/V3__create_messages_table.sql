create table messages(
    id int primary key auto_increment,
    content varchar(500) not null,
    company_id int not null,
    member_id int not null,
    delivery_type int not null,
    created_on datetime
);
alter table messages add foreign key (company_id ) references companies(id);
alter table messages add foreign key (member_id ) references members(id);