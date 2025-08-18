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
    name varchar(50) not null unique,
    type varchar(10),
    weight double precision not null,
    is_deleted boolean not null
);

drop table if exists swa_sub_criteria;

create table swa_sub_criteria(
     id bigint primary key auto_increment,
     swa_criteria_id bigint ,
     name varchar(50) not null,
     operator varchar(3),
     min_value double precision not null,
     max_value double precision not null,
     weight double precision not null,
     is_deleted boolean not null,
     FOREIGN KEY (swa_criteria_id) REFERENCES swa_criteria(id)
);


drop table if exists swa_alternative_data_source;

create table swa_alternative_data_source (
      id bigint primary key auto_increment,
      code varchar(10) not null,
      school_year varchar(9) not null,
      major varchar(20) not null,
      class_room varchar(10) not null,
      filename varchar(250) not null,
      status varchar(100) not null,
      swa_criteria_id ARRAY not null,
      is_deleted boolean not null
);
