drop table if exists `Musician`;
drop table if exists `Song`;
drop table if exists `Challenge`;
drop table if exists `Comment`;
drop table if exists `Participation`;

create table Musician (
	ID int auto_increment primary key,
	username varchar(15),
	password varchar(15),
	log_date date,
	user_type varchar(10),
	id_pic int
);

create table Song (
	ID int auto_increment primary key,
	name varchar(25),
	author varchar(25),
	album varchar(25),
	genre varchar(15),
	duration int,
	link varchar(50)
);

create table Challenge (
	ID int auto_increment primary key,
	name varchar(25),
	ID_song int,
	ID_musician int,
	ID_winner int default 0,
	creat_date date,
	fin_date date,
	descr text,
	foreign key (ID_song) references Song(ID),
	foreign key (ID_musician) references Musician(ID)
);

create table Comment (
	ID int auto_increment primary key,
	ID_musician int,
	com_date date,
	content text,
	foreign key (ID_musician) references Musician(ID)
);

create table Participation(
	ID_musician int,
	ID_chall int,
	part_date date,
	votes int default 0,
	youtube varchar(50),
	foreign key (ID_musician) references Musician(ID),
	foreign key (ID_chall) references Challenge(ID),
	constraint PK_participation primary key (id_musician, id_chall)
);

insert into `Musician` values (null, 'admin', 'admin', '2018-04-05', 'admin', '2');
insert into `Musician` values (2, 'invitado', 'invitado', '2018-04-09', 'invitado', '1');

insert into `Song` values (null, 'Cancion 1', 'Autor 1', 'Album 1', 'Rock', 3, "");
insert into `Song` values (null, 'Cancion 2', 'Autor 2', 'Album 2', 'Pop', 4, "");
insert into `Song` values (null, 'Cancion 3', 'Autor 3', 'Album 3', 'Rock', 3, "");
insert into `Song` values (null, 'Cancion 4', 'Autor 5', 'Album 5', 'Pop', 2, "");
insert into `Song` values (null, 'Cancion 5', 'Autor 2', 'Album 2', 'Rock', 8, "");
insert into `Song` values (null, 'Cancion 6', 'Autor 4', 'Album 4', 'Pop', 3, "");
insert into `Song` values (null, 'Cancion 7', 'Autor 6', 'Album 6', 'Rock', 5, "");
insert into `Song` values (null, 'Cancion 8', 'Autor 1', 'Album 1', 'Pop', 10, "");
insert into `Song` values (null, 'Cancion 9', 'Autor 4', 'Album 4', 'Rock', 5, "");
insert into `Song` values (null, 'Cancion 10', 'Autor 6', 'Album 6', 'Pop', 6, "");
insert into `Song` values (null, 'Cancion 11', 'Autor 3', 'Album 3', 'Rock', 3, "");
insert into `Song` values (null, 'Cancion 12', 'Autor 2', 'Album 2', 'Pop', 4, "");
insert into `Song` values (null, 'BrotherMan BIll', 'TerribleTim', 'Spreads Love', 'Country', 4, "");