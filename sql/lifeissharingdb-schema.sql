drop database if exists lifeissharingdb;
create database lifeissharingdb;
 
use lifeissharingdb;
 
create table usuario (
	name varchar(40) not null,
	email varchar(255) not null,
	username varchar(20) not null primary key,
	password char(32) not null
	
);

create table user_roles (
username varchar(20) not null,
rolename varchar(20) not null,
foreign key(username) references usuario(username) on delete cascade,
primary key (username, rolename)
);

create table lista (
	id int not null primary key auto_increment,
	nombre varchar(30) not null,
	fecha_creacion datetime not null default current_timestamp,
	ultima_modificacion timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	creador varchar(20) not null,
	foreign key(creador) references usuario(username) on delete cascade
);

create table editores (
	username varchar(20) not null,
	idlista int not null,
	foreign key(username) references usuario(username) on delete cascade,
	foreign key(idlista) references lista(id) on delete cascade
);


create table item (
	description varchar(100) not null, 
	id int not null, /*ID de lista*/
	iditem int not null primary key auto_increment,
	foreign key(id) references lista(id) on delete cascade
);