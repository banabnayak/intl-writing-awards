-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.15 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------


CREATE TABLE IF NOT EXISTS `app_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_value` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `student_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);



CREATE TABLE IF NOT EXISTS `class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `class_group_id_fk1` (`group_id`),
  CONSTRAINT `class_group_id_fk1` FOREIGN KEY (`group_id`) REFERENCES `student_group` (`id`)
);



CREATE TABLE IF NOT EXISTS `topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_group_id_fk1` (`group_id`),
  CONSTRAINT `topic_group_id_fk1` FOREIGN KEY (`group_id`) REFERENCES `student_group` (`id`)
);


CREATE TABLE IF NOT EXISTS `school` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` VARCHAR(16) DEFAULT NULL,
  `pincode` int(11) DEFAULT NULL,
  `principal_email` varchar(255) DEFAULT NULL,
  `principal_name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);



CREATE TABLE IF NOT EXISTS `story` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `story_text` text DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  `submission_dt` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `total_marks` int(11) DEFAULT NULL,
  `topic_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `story_topic_id_fk1` (`topic_id`),
  CONSTRAINT `story_topic_id_fk1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`)
);




CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `age` smallint(6) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `parent_email` varchar(255) DEFAULT NULL,
  `parent_name` varchar(255) DEFAULT NULL,
  `parent_phone` VARCHAR(16) DEFAULT NULL,
  `reg_no` varchar(255) DEFAULT NULL,
  `student_class` smallint(6) DEFAULT NULL,
  `student_group` smallint(6) DEFAULT NULL,
  `school_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_School_ID_fk1` (`School_ID`), 
  KEY `student_School_ID_fk2` (`School_ID`),
  CONSTRAINT `student_School_ID_FK2` FOREIGN KEY (`School_ID`) REFERENCES `school` (`id`)
);


CREATE TABLE IF NOT EXISTS `school_student` (
  `school_id` bigint(20) NOT NULL,
  `students_id` bigint(20) NOT NULL,
  PRIMARY KEY (`school_id`,`students_id`),
  UNIQUE KEY `students_id` (`students_id`),
  KEY `ss_students_id_fk1` (`students_id`),
  KEY `ss_school_id_fk2` (`school_id`),
  CONSTRAINT `ss_school_id_fk2` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  CONSTRAINT `ss_students_id_fk1` FOREIGN KEY (`students_id`) REFERENCES `student` (`id`)
);

CREATE TABLE IF NOT EXISTS `story_assignment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `assigned_to` bigint(20) DEFAULT NULL,
  `story_id` bigint(20) DEFAULT NULL,
  `total_marks` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
);




CREATE TABLE IF NOT EXISTS `storyReview` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `story_assignment_id` bigint(20) DEFAULT NULL,
  `weightage` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;



CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);



CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `reg_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);



CREATE TABLE IF NOT EXISTS `email_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `generated_date` datetime DEFAULT NULL,
  `html_body` longtext,
  `ignored_date` datetime DEFAULT NULL,
  `recipient_address` varchar(255) DEFAULT NULL,
  `sent_date` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `template_type` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;



CREATE TABLE IF NOT EXISTS `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `unit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;





CREATE TABLE IF NOT EXISTS `user_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_date` date DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;



-- //@UNDO


DROP TABLE question;

DROP TABLE email_message;

DROP TABLE user;

DROP TABLE user_audit;

DROP TABLE task;

DROP TABLE storyReview;

DROP TABLE story_assignment;

DROP TABLE story;

DROP TABLE school_student;

DROP TABLE student;

DROP TABLE school;

DROP TABLE topic;

DROP TABLE class;

DROP TABLE student_group;

DROP TABLE app_config;



