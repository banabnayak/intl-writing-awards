-- // create state data model
CREATE TABLE `state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` char(1) NOT NULL DEFAULT 'N',
  `created_by` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `state` (`id`, `name`, `created_date`,`updated_date`, `deleted`,`created_by`,`updated_by`) VALUES
(1,'Andaman and Nicobar Islands',now(),now(),0,1,1),(2,'Andhra Pradesh',now(),now(),0,1,1),(3,'Arunachal Pradesh',now(),now(),0,1,1),(4,'Assam',now(),now(),0,1,1),(5,'Bihar',now(),now(),0,1,1),(6,'Chandigarh',now(),now(),0,1,1),(7,'Chhattisgarh',now(),now(),0,1,1),(8,'Dadra and Nagar Haveli',now(),now(),0,1,1),(9,'Daman and Diu',now(),now(),0,1,1),(10,'Delhi',now(),now(),0,1,1),(11,'Goa',now(),now(),0,1,1),(12,'Gujarat',now(),now(),0,1,1),(13,'Haryana',now(),now(),0,1,1),(14,'Himachal Pradesh',now(),now(),0,1,1),(15,'Jammu and Kashmir',now(),now(),0,1,1),(16,'Jharkhand',now(),now(),0,1,1),(17,'Karnataka',now(),now(),0,1,1),(18,'Kerala',now(),now(),0,1,1),(19,'Lakshadweep',now(),now(),0,1,1),(20,'Madhya Pradesh',now(),now(),0,1,1),(21,'Maharashtra', now(),now(),0,1,1),(22,'Manipur',now(),now(),0,1,1),(23,'Meghalaya',now(),now(),0,1,1),(24,'Mizoram',now(),now(),0,1,1),(25,'Nagaland', now(),now(),0,1,1),(26,'Odisha',now(),now(),0,1,1),(27,'Puducherry',now(),now(),0,1,1),(28,'Punjab',now(),now(),0,1,1),(29,'Rajasthan', now(),now(),0,1,1),(30,'Sikkim',now(),now(),0,1,1),(31,'Tamil Nadu',now(),now(),0,1,1),(32,'Telangana',now(),now(),0,1,1),(33,'Tripura',now(),now(),0,1,1),(34,'Uttar Pradesh',now(),now(),0,1,1),(35,'Uttarakhand',now(),now(),0,1,1),(36,'West Bengal',now(),now(),0,1,1);

-- //@UNDO
DROP TABLE state;