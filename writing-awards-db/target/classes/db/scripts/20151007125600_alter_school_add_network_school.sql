-- //alter the school data model to mark network school.
ALTER TABLE `school`
ADD `is_network_school` TINYINT(1) NULL DEFAULT '0';
-- //@UNDO
ALTER TABLE `school` DROP COLUMN `is_network_school`;