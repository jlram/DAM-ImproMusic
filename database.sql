create table User (
	ID int auto_increment primary key,
	username varchar2(15),
	password varchar2(15),
	log_date date,
	user_type varchar2(10),
	id_pic int
);

create table Song (
	ID int auto_increment primary key,
	name varchar2(25),
	author varchar2(25),
	album varchar2(25),
	genre varchar2(15),
	duration int
);

create table Challenge (
	ID int auto_increment primary key,
	name varchar2(25),
	ID_song int,
	creat_date date,
	fin_date date,
	descr text,
	foreign key (ID_songD) references Song(ID)
);

create table Comment (
	ID int auto_increment primary key,
	com_date date,
	content text
);

create table Participation(
	ID_user int,
	ID_chall int,
	part_date date,
	votes int default 0,
	constraint PK_participation primary key (id_user, id_chall)
);