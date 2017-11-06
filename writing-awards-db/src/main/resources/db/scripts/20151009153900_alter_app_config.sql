-- alter app_config table
-- Migration SQL that makes the change goes here.
delete from app_config where description like 'config_incremental_value_stories';
insert into app_config(config_value,description)values('3','config_elements_stories');
insert into app_config(config_value,description)values('50','top participants');
insert into app_config(config_value,description)values('3','config_elements_participants');
-- //@UNDO

