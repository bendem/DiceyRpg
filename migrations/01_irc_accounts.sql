create table account (
    IdAccount int not null auto_increment,
    NickServAccount varchar(50) not null unique, -- All users should be registered, no password checking that way
    CreationTime datetime default CURRENT_TIMESTAMP not null,
    constraint account_pk primary key ( IdAccount )
);

create table user_has_character (
    IdAccount int not null,
    IdCharacter int not null,
    LastLogin datetime not null,
    constraint user_has_character_pk primary key ( IdAccount, IdCharacter )
);