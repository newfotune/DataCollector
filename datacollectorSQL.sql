use heroku_7d9ca065eba0788;

CREATE TABLE ENTITY_TYPE (
	entity_type_id int primary key,
	entity_name varchar(20)
);

INSERT INTO ENTITY_TYPE VALUES /*map type person value with corresponding role.*/
(1, 'gas station'),(2, 'resturant'),(3, 'stop sign'),
(4, 'traffic light'), (5, 'traffic camera'), (6, 'road construction');

CREATE TABLE USER (
	user_id int auto_increment primary key,
    first_name varchar(20) not null,
    middle_name varchar(20) not null,
    last_name varchar(20) not null,
    email varchar(30) not null,
    _password varchar(50) not null,
    
    constraint UNIQUE_EMAIL unique(email)
);

/* This is the general entity. */
CREATE TABLE ENTITY (
	entity_id int AUTO_INCREMENT primary key,
	-- every entity type must exist in out person type table
	entity_type_id int, 
    user_id int,
    time_entered timestamp DEFAULT current_timestamp,
    
    geolat float(10, 6) not null,
    geolong float(10, 6) not null,
    description varchar(100),
    
	foreign key (entity_type_id) references ENTITY_TYPE(entity_type_id),
    foreign key (user_id) references USER(user_id) on delete set null on update cascade,
	constraint unique_entity unique(entity_id, entity_type_id)
);

CREATE TABLE IMAGE (
	image_id int primary key auto_increment,
	user_id int,
    entity_id int,
    content blob not null,

    foreign key (user_id) references USER(user_id) on delete no action on update cascade,
    foreign key (entity_id) references ENTITY(entity_id) on delete cascade on update cascade
);

-- all entity types
CREATE TABLE GAS_STATION (
    entity_id int primary key,
    brand varchar(20) not null,
    
    foreign key (entity_id) references ENTITY(entity_id) on delete cascade on update cascade
);

CREATE TABLE RESTURANT (
    entity_id int primary key,
    brand varchar(20) not null,
    
    foreign key (entity_id) references ENTITY(entity_id) on delete cascade on update cascade
);

CREATE TABLE STOP_SIGN (
    entity_id int primary key,
    
    foreign key (entity_id) references ENTITY(entity_id) on delete cascade on update cascade
);

CREATE TABLE TRAFFIC_LIGHT (
    entity_id int primary key,
	
    foreign key (entity_id) references ENTITY(entity_id) on delete cascade on update cascade
);

CREATE TABLE TRAFFIC_CAMERA (
    entity_id int primary key,
    
    foreign key (entity_id) references ENTITY(entity_id) on delete cascade on update cascade
);

CREATE TABLE ROAD_CONSTRUCTION (
    entity_id int primary key,
    
    foreign key (entity_id) references ENTITY(entity_id) on delete cascade on update cascade
);