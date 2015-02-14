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
drop table if exists player;
drop table if exists player_finish_quest;
drop table if exists quest;
drop table if exists quest_made_of_room;
drop table if exists quest_rewards_item;
drop table if exists resource;
drop table if exists room;
drop table if exists room_contains_monster;

create table character (
    IdCharacter int not null auto_increment,
    constraint character_pk primary key ( IdCharacter )
);

create table player_equips_equipment (
    IdCharacter int not null,
    IdItem int not null,
    constraint character_equips_equipment_pk primary key ( IdCharacter, IdItem )
);

create table character_has_attribute (
    IdCharacter int not null,
    Attribute tinyint not null, -- enum
    Value int not null,
    constraint character_has_attribute_pk primary key ( IdCharacter, Attribute )
);

create table player_has_item (
    IdCharacter int not null,
    IdItem int not null,
    Count tinyint not null,
    constraint player_has_item_pk primary key ( IdCharacter, IdItem )
);

create table player_equips_die_in_dice_set (
    IdCharacter int not null,
    IdItem int not null,
    IdDiceSet int not null,
    constraint player_equips_die_in_dice_set_pk primary key ( IdCharacter, IdItem, IdDiceSet )
);

create table climate (
    IdClimate int not null auto_increment,
    Name varchar(50) not null,
    Description clob,
    constraint climate_pk primary key ( IdClimate )
);

create table climate_modifies_attribute (
    IdClimate int not null,
    Attribute tinyint not null, -- enum
    Modifier smallint not null, -- enum
    constraint climate_modifies_attribute_pk primary key ( IdClimate, Attribute )
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
    IdItem int not null,
    Count smallint not null,
    constraint crafting_requires_resource_pk primary key ( IdCraftingRecipe, IdItem )
);

create table dice_set (
    IdItem int not null auto_increment,
    NumberOfDice tinyint not null,
    constraint dice_set_pk primary key ( IdItem )
);

create table die (
    IdItem int not null auto_increment,
    Type tinyint not null, -- enum
    Min tinyint not null,
    Max tinyint not null,
    CanFail boolean default true not null,
    constraint die_pk primary key ( IdItem )
);

create table equipment (
    IdItem int not null auto_increment,
    EquipableSlot tinyint, -- enum, null = not equipable
    constraint equipment_pk primary key ( IdItem )
);

create table equipment_modifies_attribute (
    IdItem int not null,
    Attribute tinyint not null, -- enum
    Modifier smallint not null, -- enum
    constraint equipment_modifies_attribute_pk primary key ( IdItem, Attribute )
);

create table item (
    IdItem int not null auto_increment,
    Name varchar(50),
    Description clob,
    Value int not null, -- for selling to bot shop
    Rank tinyint not null, -- enum, def?
    LevelRequired smallint not null, -- Level required to equip (people should have more chances to drop stuff close to or below their level)
    DropProbability smallint not null, -- 1 chance out of the value
    DropClimate int, -- the climate the item can be dropped in (or null if not bound to a climate)
    constraint item_pk primary key ( IdItem )
);

create table monster (
    IdCharacter int not null auto_increment,
    Name varchar(50) not null,
    Boss boolean default false not null,
    Description clob,
    constraint monster_pk primary key ( IdCharacter )
);

create table monster_has_die (
    IdCharacter int not null,
    IdItem int not null,
    constraint monster_has_die_pk primary key ( IdCharacter, IdItem )
);

create table player (
    IdCharacter int not null auto_increment,
    Username varchar(50) not null, -- unique
    Password varchar(255) not null, -- should move to an account table with register time, last login and such informations
    Level smallint default 1 not null,
    Experience int default 0 not null,
    Money bigint default 0 not null,
    UnusedAttributePointCount int default 0 not null,
    constraint player_pk primary key ( IdCharacter )
);

create table player_finish_quest (
    IdCharacter int not null,
    IdQuest int not null,
    DateCompletion timestamp default current_timestamp not null,
    constraint player_finish_quest_pk primary key ( IdCharacter, IdQuest )
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
    IdItem int not null auto_increment,
    constraint resource_pk primary key ( IdItem )
);

create table room (
    IdRoom int not null auto_increment,
    constraint room_pk primary key ( IdRoom )
);

create table room_contains_monster (
    IdRoom int not null,
    IdCharacter int not null,
    Level smallint not null,
    Count tinyint not null,
    constraint room_contains_monster_pk primary key ( IdRoom, IdCharacter, Level )
);

-- Foreign keys
alter table player_equips_equipment add constraint player_equips_equipment_player_fk foreign key ( IdCharacter ) references player ( IdCharacter );
alter table player_equips_equipment add constraint player_equips_equipment_equipment_fk foreign key ( IdItem ) references equipment ( IdItem );
alter table character_has_attribute add constraint character_has_attribute_character_fk foreign key ( IdCharacter ) references character ( IdCharacter );
alter table player_has_item add constraint player_has_item_character_fk foreign key ( IdCharacter ) references player ( IdCharacter );
alter table player_has_item add constraint player_has_item_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table player_equips_die_in_dice_set add constraint player_equips_die_in_dice_set_character_fk foreign key ( IdCharacter ) references player ( IdCharacter );
alter table player_equips_die_in_dice_set add constraint player_equips_die_in_dice_set_dice_set_fk foreign key ( IdDiceSet ) references dice_set ( IdItem );
alter table player_equips_die_in_dice_set add constraint player_equips_die_in_dice_set_die_fk foreign key ( IdItem ) references die ( IdItem );
alter table climate_modifies_attribute add constraint climate_modifies_attribute_climate_fk foreign key ( IdClimate ) references climate ( IdClimate );
alter table crafting_recipe add constraint crafting_recipe_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table crafting_requires_resource add constraint crafting_requires_resource_crafting_recipe_fk foreign key ( IdCraftingRecipe ) references crafting_recipe ( IdCraftingRecipe );
alter table crafting_requires_resource add constraint crafting_requires_resource_resource_fk foreign key ( IdItem ) references resource ( IdItem );
alter table dice_set add constraint dice_set_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table die add constraint die_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table equipment add constraint equipment_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table equipment_modifies_attribute add constraint equipment_modifies_attribute_equipment_fk foreign key ( IdItem ) references equipment ( IdItem );
alter table player add constraint player_character_fk foreign key ( IdCharacter ) references character ( IdCharacter );
alter table player_finish_quest add constraint player_finish_quest_player_fk foreign key ( IdCharacter ) references player ( IdCharacter );
alter table player_finish_quest add constraint player_finish_quest_quest_fk foreign key ( IdQuest ) references quest ( IdQuest );
alter table quest add constraint quest_climate_fk foreign key ( IdClimate ) references climate ( IdClimate );
alter table quest_made_of_room add constraint quest_made_of_room_quest_fk foreign key ( IdQuest ) references quest ( IdQuest );
alter table quest_made_of_room add constraint quest_made_of_room_room_fk foreign key ( IdRoom ) references room ( IdRoom );
alter table quest_rewards_item add constraint quest_rewards_item_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table quest_rewards_item add constraint quest_rewards_item_quest_fk foreign key ( IdQuest ) references quest ( IdQuest );
alter table resource add constraint resource_item_fk foreign key ( IdItem ) references item ( IdItem );
alter table room_contains_monster add constraint room_contains_monster_monster_fk foreign key ( IdCharacter ) references monster ( IdCharacter );
alter table room_contains_monster add constraint room_contains_monster_room_fk foreign key ( IdRoom ) references room ( IdRoom );
alter table monster add constraint monster_character_fk foreign key ( IdCharacter ) references character ( IdCharacter );
alter table monster_has_die add constraint monster_has_die_monster_fk foreign key ( IdCharacter ) references monster ( IdCharacter );
alter table monster_has_die add constraint monster_has_die_die_fk foreign key ( IdItem ) references die ( IdItem );
