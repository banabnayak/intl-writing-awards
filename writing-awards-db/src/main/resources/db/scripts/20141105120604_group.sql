-- // add_group_data
-- Migration SQL that makes the change goes here.
INSERT INTO `student_group` (`ID`, `description`, `name`, `created_by`, `created_date`, `deleted`, `updated_by`, `updated_date`) VALUES
	(1, 'GROUP 1', 'GROUP 1', NULL, NULL, 0, NULL, NULL),
	(2, 'GROUP 2', 'GROUP 2', NULL, NULL, 0, NULL, NULL);




-- //@UNDO

DELETE FROM student_group;
