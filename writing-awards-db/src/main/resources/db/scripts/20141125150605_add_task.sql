-- // add task descriptions and details
-- Migration SQL that makes the change goes here.
INSERT INTO `task` (`id`, `created_by`, `created_date`, `deleted`, `updated_by`, `updated_date`, `description`, `end_date`, `start_date`) VALUES
	(1, 1, '2014-11-25', 0, 1, '2014-11-25', 'Registration and Submission', '2015-02-14 23:59:59', '2014-12-20 00:00:00'),
	(2, 1, '2014-12-03', 0, 1, '2014-12-03', 'Story Rating', '2015-03-10 23:59:59', '2015-02-15 00:00:00'),
	(3, 1, '2014-11-25', 0, 1, '2014-11-25', 'Winner Announcement', '2015-04-10 23:59:59', '2015-04-01 00:00:00');

-- //@UNDO
DELETE FROM task;