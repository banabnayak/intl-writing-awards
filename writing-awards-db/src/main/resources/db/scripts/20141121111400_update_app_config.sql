-- new table app_config
-- Migration SQL that makes the change goes here.

insert into app_config(config_value,description)values('25','top stories');
insert into app_config(config_value,description)values('5','config_incremental_value_stories');
insert into app_config(config_value,description)values('127.0.0.1','smtp_host');
insert into app_config(config_value,description)values('swa@scholastic.co.in','from_address');
insert into app_config(config_value,description)values('www.scholastic.co.in','resultLink');
insert into app_config(config_value,description)values('40','totalMarks');
insert into app_config(config_value,description)values('5','bonusMarks');

-- //@UNDO
-- SQL to undo the change goes here.
delete from app_config where description like 'top stories';
delete from app_config where description like 'config_incremental_value_stories';
delete from app_config where description like 'resultLink';
delete from app_config where description like 'totalMarks';
delete from app_config where description like 'bonusMarks';

