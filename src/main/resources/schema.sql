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
    role varchar(100) not null
);

drop table if exists sessions;

create table sessions (
      id varchar(200) primary key,
      session varchar(2000) not null
);