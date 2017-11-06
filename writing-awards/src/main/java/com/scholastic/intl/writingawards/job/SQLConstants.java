package com.scholastic.intl.writingawards.job;

import java.io.Serializable;

/**
 * @author madhusmita.nayak
 *
 */
public class SQLConstants implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String GET_STUDENT_DETAILS=""
			+ "SELECT s.id AS studentId, s.full_name AS fullName, "
			+ "s.age, s.parent_email AS parentEmail, s.parent_name AS parentName, "
			+ "s.parent_phone AS parentPhone, s.reg_no AS regNo, "
			+ "c.name AS className, c.description AS classDesc, "
			+ "g.name AS groupName, g.description AS groupDesc, "
			+ "sc.address, sc.city, sc.name AS schoolName, sc.phone, sc.pincode, "
			+ "sc.principal_email AS principalEmail, sc.principal_name AS principalName, "
			+ "sc.state FROM student s INNER JOIN class c ON c.id=s.student_class "
			+ "INNER JOIN student_group g ON g.id=s.student_group INNER JOIN school sc "
			+ "ON sc.id=s.school_id WHERE s.deleted=0";
	
	public static final String FIND_STUDENTS_WITH_STORY_DETAILS=""
			+ "SELECT s.full_name AS studentName, s.reg_no AS registrationNumber,"
			+ " sc.name AS schoolName, g.name AS groupName, st.title AS storyTitle,"
			+ "st.story_text AS storyText , t.name AS topicName	FROM student s INNER JOIN "
			+ "student_group g ON g.id=s.student_group INNER JOIN school sc ON sc.id=s.School_ID "
			+ "	INNER JOIN story st ON st.student_id=s.id INNER JOIN topic t on t.id = st.topic_id "
			+ "	WHERE s.deleted=0  and g.id= :groupId ORDER BY st.total_marks DESC";
	
	public static final String STORY_ASSIGNMENTS = ""
			  + "SELECT st.student_id as studentId,  st.id as storyId, sd.student_group as studentGroup, "
			  + " sd.student_class as studentClass,  sc.city as city, sc.state as state, "
			  + " sc.id as school FROM  student sd,  story st, school sc  WHERE  st.student_id = sd.id "
			  + " AND  sd.School_ID = sc.id  AND  st.submission_dt IS NOT NULL ORDER BY studentId";
	
	public static final String TOPICS_BY_USER = ""
			  + "SELECT  tp.id as id, tp.name as name FROM student sd, topic tp WHERE "
			  + " sd.parent_email =? AND sd.student_group = tp.group_id AND tp.deleted=0 ";
	
	public static final String ASSIGNMETN_BY_USER = ""
			  + "SELECT st.id as id, st.topic_id as topic, st.title as title, "
			  + " st.story_text as storyText, sa.total_marks as totalMarks FROM "
			  + " story st, story_assignment sa WHERE sa.assigned_to =? AND st.id = sa.story_id " ;

	public static final String STORY_RATING_MARK_ZERO=""
			+ "SELECT s.id FROM story s WHERE s.student_id IN "
			+ " (SELECT sa.assigned_to FROM story_assignment sa INNER JOIN story s " 
			+ "ON sa.story_id=s.id WHERE sa.story_id IN "
			+ " (SELECT s.id FROM story s INNER JOIN student st ON s.student_id=st.id " 
			+ " WHERE s.total_marks<>0 AND s.deleted=0) AND s.total_marks=0)";

	public static final String LIST_NETWORK_SCHOOL_PARTICIPATED = ""
			+ "SELECT sc.name,sc.address,sc.city,sc.phone,sc.pincode,sc.principal_email AS principalEmail,"
			+ " sc.principal_name AS principalName,sc.state"
			+ " FROM student sd, school sc"
			+ " WHERE sd.school_id = sc.id  AND sc.is_network_school = 1 "
			+ "AND sc.deleted=0 and sd.deleted=0 "
			+ "ORDER BY name";
	public static final String LIST_NON_NETWORK_SCHOOL_PARTICIPATED = ""
			+ "SELECT sc.name,sc.address,sc.city,sc.phone,sc.pincode,sc.principal_email AS principalEmail, "
			+ "sc.principal_name AS principalName, sc.state "
			+ "FROM student sd, school sc WHERE sd.school_id = sc.id  AND sc.is_network_school = 0 "
			+ "AND sc.deleted=0 and sd.deleted=0 "
			+ "ORDER BY name";
	public static final String LIST_NETWORK_SCHOOL_NOT_PARTICIPATED = ""
			+ "SELECT sc.name,sc.address,sc.city,sc.phone,sc.pincode,sc.principal_email AS principalEmail,"
			+ " sc.principal_name AS principalName,sc.state "
			+ " FROM school sc where sc.is_network_school=1 and sc.id NOT IN (SELECT  DISTINCT sc.id FROM student sd,"
			+ " school sc WHERE sd.school_id = sc.id  AND sc.is_network_school=1"
			+ " AND sc.deleted=0 AND sd.deleted=0) ORDER BY name";

	public static final String LIST_ALL_STORY_SUBMITTED_STUDENTS = ""
			+ "SELECT sd.id AS studentId, sd.full_name AS fullName, "
			+ "sd.age, sd.parent_email AS parentEmail, "
			+ "sd.parent_name AS parentName, "
			+ "sd.parent_phone AS parentPhone, sd.reg_no AS regNo, "
			+ "c.name AS className, c.description AS classDesc, "
			+ "g.name AS groupName, g.description AS groupDesc, "
			+ "sc.address, sc.city, sc.name AS schoolName, "
			+ "sc.phone,sc.pincode, "
			+ "sc.principal_email AS principalEmail, "
			+ "sc.principal_name AS principalName, sc.state, tp.name AS topicName  FROM "
			+ " student sd, story st, school sc , "
			+ "class c , student_group g , topic tp WHERE c.id=sd.student_class  AND g.id=sd.student_group AND "
			+ "st.student_id = sd.id AND  sd.school_id = sc.id AND st.topic_id = tp.id "
			+ "AND st.submission_dt IS NOT NULL ORDER BY full_name";

	public static final String LIST_ALL_TOP_PARTICIPATED_STUDENTS =  ""
			+ "SELECT sd.id AS studentId, sd.full_name AS fullName, "
			+ "sd.age, sd.parent_email AS parentEmail, "
			+ "sd.parent_name AS parentName, "
			+ "sd.parent_phone AS parentPhone, sd.reg_no AS regNo, "
			+ "c.name AS className, c.description AS classDesc, "
			+ "g.name AS groupName, g.description AS groupDesc, "
			+ "sc.address, sc.city, sc.name AS schoolName, "
			+ "sc.phone,sc.pincode, "
			+ "sc.principal_email AS principalEmail, "
			+ "sc.principal_name AS principalName, sc.state, tp.name AS topicName FROM "
			+ " student sd, story st, school sc , "
			+ "class c , student_group g , topic tp WHERE c.id=sd.student_class  AND g.id = sd.student_group AND "
			+ "st.student_id = sd.id AND  sd.school_id = sc.id AND g.id =:groupId AND st.topic_id = tp.id "
			+ "AND st.submission_dt IS NOT NULL ORDER BY st.total_marks DESC";
	public static final String GET_ALL_SCHOOL_DETAILS=""
			+ "SELECT s.id AS studentId, "		
			+ "s.full_name AS fullName, "
			+ "s.age, "
			+ "s.parent_email AS parentEmail, "
			+ "s.parent_name AS parentName, "
			+ "s.parent_phone AS parentPhone, "
			+ "s.reg_no AS regNo, "
			+ "c.name AS className, "
			+ "c.description AS classDesc, "
			+ "g.name AS groupName, "
			+ "g.description AS groupDesc, "
			+ "sc.address, "
			+ "sc.city, "
			+ "sc.name AS schoolName, "
			+ "sc.phone, "
			+ "sc.pincode, "
			+ "sc.principal_email AS principalEmail, "
			+ "sc.principal_name AS principalName, "
			+ "sc.state "
			+ "FROM school sc "			
			+ "LEFT OUTER JOIN student s ON s.school_id = sc.id  "
			+ "LEFT OUTER JOIN class c ON c.id=s.student_class "
			+ "LEFT OUTER JOIN student_group g ON g.id=s.student_group "
			+ "WHERE sc.deleted=0";
	
	private SQLConstants() {
	}
}
