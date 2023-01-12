create table taken_for (
    id int primary key auto_increment,
    message_id int not null,
    member_id int not null,
    foreign key (message_id) references messages(id),
    foreign key (member_id) references members(id)
)
