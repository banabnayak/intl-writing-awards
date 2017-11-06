-- // add_class_data
-- Migration SQL that makes the change goes here.
INSERT INTO `class` (`ID`, `description`, `name`, `group_id`, `created_by`, `created_date`, `deleted`, `updated_by`, `updated_date`) VALUES
	(1, 'CLASS 4', 'CLASS 4', 1, 1, '2014-11-20', 0, 1, '2014-11-20'),
	(2, 'CLASS 5', 'CLASS 5', 1, 1, '2014-11-20', 0, 1, '2014-11-20'),
	(3, 'CLASS 6', 'CLASS 6', 1, 1, '2014-11-20', 0, 1, '2014-11-20'),
	(4, 'CLASS 7', 'CLASS 7', 2, 1, '2014-11-20', 0, 1, '2014-11-20'),
	(5, 'CLASS 8', 'CLASS 8', 2, 1, '2014-11-20', 0, 1, '2014-11-20'),
	(6, 'CLASS 9', 'CLASS 9', 2, 1, '2014-11-20', 0, 1, '2014-11-20');




-- //@UNDO
DELETE FROM class;