/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  KBDSI-IQBAL
 * Created: Feb 4, 2025
 */

drop table if exists user_info;

create table user_info (
    id bigint primary key auto_increment,
    username varchar(100) not null unique,
    email varchar(100) not null unique,
    password varchar(100) not null,
    role varchar(100) not null,
    is_deleted boolean not null
);

drop table if exists sessions;

create table sessions (
      id varchar(200) primary key,
      session varchar(2000) not null
);

drop table if exists swa_criteria;

create table swa_criteria(
    id bigint primary key auto_increment,
    name varchar(10) not null unique,
    weight double precision not null,
    ordering int not null,
    is_deleted boolean not null
);

drop table if exists swa_alternative_data_source;

create table swa_alternative_data_source (
      id bigint primary key auto_increment,
      code varchar(10) not null,
      school_year varchar(4) not null,
      major varchar(20) not null,
      class_room varchar(10) not null,
      file_source BLOB not null,
      status varchar(100) not null,
      is_deleted boolean not null
);
