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


insert into swa_criteria(name, type, weight, is_deleted) VALUES('Nilai Akhir','Benefit',0.65,false);
insert into swa_criteria(name, type, weight, is_deleted) VALUES('Nilai Sertifikat','Benefit',0.25,false);
insert into swa_criteria(name, type, weight, is_deleted) VALUES('Nilai Sikap','Benefit',0.1,false);


insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(1,'Nilai kurang dari 40','<=>',0.0,40.0,1.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(1,'Nilai kurang dari 55','<=>',40.0,55.0,3.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(1,'Nilai kurang dari 70','<=>',55.0,70.0,5.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(1,'Nilai kurang dari 85','<=>',70.0,85.0,7.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(1,'Nilai lebih dari 85','>=',85.0,0.0,9.0,false);

insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(2,'Nilai kurang dari 2','<=',0.0,2.0,1.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator, min_value, max_value,weight, is_deleted) VALUES(2,'Nilai adalah 3','=',0.0,3.0,3.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator, min_value, max_value,weight, is_deleted) VALUES(2,'Nilai adalah 4','=',0.0,4.0,5.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(2,'Nilai lebih dari 5','>=',5.0,0.0,7.0,false);

insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(3,'Nilai kurang dari 2','<=',0.0,2.0,1.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator, min_value, max_value,weight, is_deleted) VALUES(3,'Nilai adalah 3','=',0.0,3.0,3.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator, min_value, max_value,weight, is_deleted) VALUES(3,'Nilai adalah 4','=',0.0,4.0,5.0,false);
insert into swa_sub_criteria(swa_criteria_id, name, operator,  min_value, max_value,weight,is_deleted) VALUES(3,'Nilai lebih dari 5','>=',5.0,0.0,7.0,false);