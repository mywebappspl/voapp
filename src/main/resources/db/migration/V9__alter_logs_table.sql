alter table logs add company_id int;
alter table logs add foreign key (company_id) references companies(id);
