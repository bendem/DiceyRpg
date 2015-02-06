drop table if exists attribute;
drop table if exists character;
drop table if exists player_equips_equipment;
drop table if exists character_has_attribute;
drop table if exists player_has_item;
drop table if exists player_equips_die_in_dice_set;
drop table if exists climate;
drop table if exists climate_modifies_attribute;
drop table if exists crafting_recipe;
drop table if exists crafting_requires_resource;
drop table if exists dice_set;
drop table if exists die;
drop table if exists equipment;
drop table if exists equipment_modifies_attribute;
drop table if exists item;
drop table if exists monster;
drop table if exists monster_has_die;
drop table if exists monster_can_drop_item;
drop table if exists player;
drop table if exists player_finish_quest;
drop table if exists quest;
drop table if exists quest_made_of_room;
drop table if exists quest_rewards_item;
drop table if exists resource;
drop table if exists room;
drop table if exists room_contains_monster;

create table attribute (
    IdAttribute int not null auto_increment,
    Name varchar(50) not null,
    constraint attribute_pk primary key ( IdAttribute )
);

create table character (
    IdCharacter int not null auto_increment,
    constraint character_pk primary key ( IdCharacter )
);

create table player_equips_equipment (
    IdPlayer int not null,
    IdEquipment int not null,
    constraint character_equips_equipment_pk primary key ( IdPlayer, IdEquipment )
);

create table character_has_attribute (
    IdCharacter int not null,
    IdAttribute int not null,
    Value int not null,
    constraint character_has_attribute_pk primary key ( IdCharacter, IdAttribute )
);

create table player_has_item (
    IdPlayer int not null,
    IdItem int not null,
    Count tinyint not null,
    constraint player_has_item_pk primary key ( IdPlayer, IdItem )
);

create table player_equips_die_in_dice_set (
    IdPlayer int not null,
    IdDie int not null,
    IdDiceSet int not null,
    constraint player_equips_die_in_dice_set_pk primary key ( IdPlayer, IdDie, IdDiceSet )
);

create table climate (
    IdClimate int not null auto_increment,
    Description clob,
    constraint climate_pk primary key ( IdClimate )
);

create table climate_modifies_attribute (
    IdClimate int not null,
    IdAttribute int not null,
    Modifier smallint not null, -- enum
    constraint climate_modifies_attribute_pk primary key ( IdClimate, IdAttribute )
);

create table crafting_recipe (
    IdCraftingRecipe int not null auto_increment,
    IdItem int not null,
    Count tinyint default 1 not null,
    LevelRequired smallint,
    constraint crafting_recipe_pk primary key ( IdCraftingRecipe )
);

create table crafting_requires_resource (
    IdCraftingRecipe int not null,
    IdResource int not null,
    Count smallint not null,
    constraint crafting_requires_resource_pk primary key ( IdCraftingRecipe, IdResource )
);

create table dice_set (
    IdDiceSet int not null auto_increment,
    NumberOfDice tinyint not null,
    constraint dice_set_pk primary key ( IdDiceSet )
);

create table die (
    IdDie int not null auto_increment,
    Type tinyint not null, -- enum
    Min tinyint not null,
    Max tinyint not null,
    CanFail boolean default true not null,
    constraint die_pk primary key ( IdDie )
);

create table equipment (
    IdEquipment int not null auto_increment,
    EquipableSlot tinyint, -- enum, null = not equipable
    constraint equipment_pk primary key ( IdEquipment )
);

create table equipment_modifies_attribute (
    IdEquipment int not null,
    IdAttribute int not null,
    Modifier smallint not null, -- enum
    constraint equipment_modifies_attribute_pk primary key ( IdEquipment, IdAttribute )
);

create table item (
    IdItem int not null auto_increment,
    Name varchar(50),
    Description clob,
    Value int not null,
    Rank tinyint not null, -- enum, def?
    constraint item_pk primary key ( IdItem )
);

create table monster (
    IdMonster int not null auto_increment,
    Boss boolean default false not null,
    Description clob,
    constraint monster_pk primary key ( IdMonster )
);

create table monster_has_die (
    IdMonster int not null,
    IdDie int not null,
    constraint monster_has_die_pk primary key ( IdMonster, IdDie )
);

create table monster_can_drop_item (
    IdMonster int not null,
    IdItem int not null,
    Count smallint not null,
    Probability tinyint not null, -- percent represented from 0 to 100
    constraint monster_can_drop_item_pk primary key ( IdMonster, IdItem )
);

create table player (
    IdPlayer int not null auto_increment,
    Username varchar(50) not null, -- unique
    Password varchar(255) not null,
    Level smallint default 1 not null,
    Experience int default 0 not null,
    Money bigint default 0 not null,
    constraint player_pk primary key ( IdPlayer )
);

create table player_finish_quest (
    IdPlayer int not null,
    IdQuest int not null,
    DateCompletion timestamp default current_timestamp not null,
    constraint player_finish_quest_pk primary key ( IdPlayer, IdQuest )
);

create table quest (
    IdQuest int not null auto_increment,
    IdClimate int not null,
    Description clob,
    constraint quest_pk primary key ( IdQuest )
);

create table quest_made_of_room (
    IdQuest int not null,
    IdRoom int not null,
    OrderNr tinyint not null,
    constraint quest_made_of_room_pk primary key ( IdQuest, IdRoom )
);

create table quest_rewards_item (
    IdQuest int not null,
    IdItem int not null,
    Count smallint not null,
    constraint quest_rewards_item_pk primary key ( IdQuest, IdItem )
);

create table resource (
    IdResource int not null auto_increment,
    constraint resource_pk primary key ( IdResource )
);

create table room (
    IdRoom int not null auto_increment,
    constraint room_pk primary key ( IdRoom )
);

create table room_contains_monster (
    IdRoom int not null,
    IdMonster int not null,
    Count tinyint not null,
    constraint room_contains_monster_pk primary key ( IdRoom, IdMonster )
);

-- Foreign keys
alter table player_equips_equipment add constraint player_equips_equipment_player_fk foreign key ( IdPlayer ) references player ( IdPlayer );
alter table player_equips_equipment add constraint player_equips_equipment_equipment_fk foreign key ( IdEquipment ) references equipment ( IdEquipment );
alter table character_has_attribute add constraint character_has_attribute_attribute_fk foreign key ( IdAttribute ) references attribute ( IdAttribute );
alter table character_has_attribute add constraint character_has_attribute_character_fk foreign key ( IdCharacter ) references character ( IdCharacter );
alter table player_has_item add constraint player_has_item_character_fk foreign key ( IdPlayer ) references player ( IdPlayer );
alter table player_has_item add constraint player_has_item_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table player_equips_die_in_dice_set add constraint player_equips_die_in_dice_set_character_fk foreign key ( IdPlayer ) references player ( IdPlayer );
alter table player_equips_die_in_dice_set add constraint player_equips_die_in_dice_set_dice_set_fk foreign key ( IdDiceSet ) references dice_set ( IdDiceSet );
alter table player_equips_die_in_dice_set add constraint player_equips_die_in_dice_set_die_fk foreign key ( IdDie ) references die ( IdDie );
alter table climate_modifies_attribute add constraint climate_modifies_attribute_attribute_fk foreign key ( IdAttribute ) references attribute ( IdAttribute );
alter table climate_modifies_attribute add constraint climate_modifies_attribute_climate_fk foreign key ( IdClimate ) references climate ( IdClimate );
alter table crafting_recipe add constraint crafting_recipe_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table crafting_requires_resource add constraint crafting_requires_resource_crafting_recipe_fk foreign key ( IdCraftingRecipe ) references crafting_recipe ( IdCraftingRecipe );
alter table crafting_requires_resource add constraint crafting_requires_resource_resource_fk foreign key ( IdResource ) references resource ( IdResource );
alter table dice_set add constraint dice_set_item_fk foreign key ( IdDiceSet ) references item ( IdItem );
alter table die add constraint die_item_fk foreign key ( IdDie ) references item ( IdItem );
alter table equipment add constraint equipment_item_fk foreign key ( IdEquipment ) references item ( IdItem );
alter table equipment_modifies_attribute add constraint equipment_modifies_attribute_attribute_fk foreign key ( IdAttribute ) references attribute ( IdAttribute );
alter table equipment_modifies_attribute add constraint equipment_modifies_attribute_equipment_fk foreign key ( IdEquipment ) references equipment ( IdEquipment );
alter table monster_can_drop_item add constraint monster_can_drop_item_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table monster_can_drop_item add constraint monster_can_drop_item_monster_fk foreign key ( IdMonster ) references monster ( IdMonster );
alter table player add constraint player_character_fk foreign key ( IdPlayer ) references character ( IdCharacter );
alter table player_finish_quest add constraint player_finish_quest_player_fk foreign key ( IdPlayer ) references player ( IdPlayer );
alter table player_finish_quest add constraint player_finish_quest_quest_fk foreign key ( IdQuest ) references quest ( IdQuest );
alter table quest add constraint quest_climate_fk foreign key ( IdClimate ) references climate ( IdClimate );
alter table quest_made_of_room add constraint quest_made_of_room_quest_fk foreign key ( IdQuest ) references quest ( IdQuest );
alter table quest_made_of_room add constraint quest_made_of_room_room_fk foreign key ( IdRoom ) references room ( IdRoom );
alter table quest_rewards_item add constraint quest_rewards_item_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table quest_rewards_item add constraint quest_rewards_item_quest_fk foreign key ( IdQuest ) references quest ( IdQuest );
alter table resource add constraint resource_item_fk foreign key ( IdResource ) references item ( IdItem );
alter table room_contains_monster add constraint room_contains_monster_monster_fk foreign key ( IdMonster ) references monster ( IdMonster );
alter table room_contains_monster add constraint room_contains_monster_room_fk foreign key ( IdRoom ) references room ( IdRoom );
alter table monster add constraint monster_character_fk foreign key ( IdMonster ) references character ( IdCharacter );
alter table monster_has_die add constraint monster_has_die_monster_fk foreign key ( IdMonster ) references monster ( IdMonster );
alter table monster_has_die add constraint monster_has_die_die_fk foreign key ( IdDie ) references die ( IdDie );
