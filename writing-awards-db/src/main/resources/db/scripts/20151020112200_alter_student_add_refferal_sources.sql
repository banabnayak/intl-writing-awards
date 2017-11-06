-- //alter the student data model to mark referral sources.
ALTER TABLE `student`
ADD `refferal_sources` VARCHAR(255) DEFAULT NULL;
-- //@UNDO
ALTER TABLE `student` DROP COLUMN `refferal_sources`;