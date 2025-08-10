/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  KBDSI-IQBAL
 * Created: Feb 4, 2025
 */

insert into user_info (username,email,password,role,is_deleted) VALUES('tester','tester@gmail.com','12345','admin',false);
insert into user_info (username,email,password,role,is_deleted) VALUES('iqbal','iqbalolala@gmail.com','12345','user',false);
insert into user_info (username,email,password,role,is_deleted) VALUES('balbal','balbalcikibal@gmail.com','12345','user',false);


insert into swa_criteria(name, type, weight, is_deleted) VALUES('Nilai Akhir','Benefit',0.5,false);
insert into swa_criteria(name, type, weight, is_deleted) VALUES('Nilai Ekstrakulikuler','Benefit',0.2,false);
insert into swa_criteria(name, type, weight, is_deleted) VALUES('Nilai Sikap','Benefit',0.2,false);
insert into swa_criteria(name, type, weight, is_deleted) VALUES('Absensi','Benefit',0.1,false);

insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(1,'Nilai diantara 0 dan 25','<=>',0.0,25.0,1.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(1,'Nilai diantara 25 dan 50','<=>',25.0,50.0,3.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(1,'Nilai diantara 50 dan 75','<=>',50.0,75.0,5.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(1,'Nilai diantara 75 dan 90','<=>',75.0,90.0,7.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(1,'Nilai diantara 90 dan 100','<=>',90.0,100.0,9.0,false);

insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(2,'Nilai diantara 0 dan 55','<=>',0.0,55.0,1.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator, min_value, max_value,weight, is_deleted) VALUES(2,'Nilai diantara 55 dan 85','<=>',55.0,85.0,3.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(2,'Nilai diantara 85 dan 100','<=>',85.0,100.0,5.0,false);

insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(3,'Nilai diantara 0 dan 55','<=>',0.0,55.0,1.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator, min_value, max_value,weight, is_deleted) VALUES(3,'Nilai diantara 55 dan 85','<=>',55.0,85.0,3.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(3,'Nilai diantara 85 dan 100','<=>',85.0,100.0,5.0,false);

insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(4,'Nilai diantara 0 dan 55','<=>',0.0,55.0,1.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator, min_value, max_value,weight, is_deleted) VALUES(4,'Nilai diantara 55 dan 85','<=>',55.0,85.0,3.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(4,'Nilai diantara 85 dan 100','<=>',85.0,100.0,5.0,false);