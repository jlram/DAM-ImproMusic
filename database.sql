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
	duration int
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
	foreign key (ID_musician) references Musician(ID),
	foreign key (ID_chall) references Challenge(ID),
	constraint PK_participation primary key (id_musician, id_chall)
);

insert into `Musician` values (null, 'admin', 'admin', '2018-04-05', 'admin', '2');