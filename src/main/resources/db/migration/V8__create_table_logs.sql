create table logs(
    id int primary key auto_increment,
    created_on datetime,
    message varchar(500) not null,
    agent_id int not null,
    foreign key (agent_id) references agents(id)
);