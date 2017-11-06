package com.scholastic.intl.writingawards.dao.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.AppConfig;
import com.scholastic.intl.writingawards.job.SQLConstants;
import com.scholastic.intl.writingawards.model.StudentDetailsVO;
import com.scholastic.intl.writingawards.model.StudentPdfVO;

/**
 * @author madhusmita.nayak
 * 
 */
@Stateless
public class StudentDetailsDaoImpl extends GenericDAOSupport<AppConfig, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * LOGGER for the current class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentDetailsDaoImpl.class);

	@Inject
	private DAOUtilty daoUtil;

	public List<AppConfig> getAppConfig() {
		List<AppConfig> results = null;
		try {
			final Query query = getDAOManager().createNamedQuery(AppConfig.FIND_ALL, AppConfig.class);
			results = query.getResultList();
			LOGGER.info("app config details retrieved successfully");
		} catch (Exception exception) {
			LOGGER.error("Exception occurs at getAppConfig method",
					exception.getMessage());
		}
		return results;
	}
	public Map<String, LinkedHashMap<String, Object>> retriveStudentDetails() {
		Map<String, LinkedHashMap<String, Object>> studentList = null;
		try {
			final Query query = getDAOManager().createNativeQuery(SQLConstants.GET_STUDENT_DETAILS);
			final ResultTransformer transformer = Transformers.aliasToBean(StudentDetailsVO.class);
			daoUtil.applyTransformer(query, transformer);
			final List<StudentDetailsVO> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				studentList = getStudentDetails(results);
			}
			LOGGER.info("student details retrieved successfully");
		} catch (Exception exception) {
			LOGGER.error("Exception occurs at retriveStudentDetails method",
					exception.getMessage());
		}
		return studentList;
	}

	private Map<String, LinkedHashMap<String, Object>> getStudentDetails(
			final List<StudentDetailsVO> results) {
		final HashMap<String, LinkedHashMap<String, Object>> 
			returnStudentDetails = new HashMap<String, LinkedHashMap<String, Object>>();
		iterateStudentInfo(returnStudentDetails,results);
		return returnStudentDetails;
	}

	public List<StudentPdfVO> createPdfVOList(final int topStories, final int groupId) {
		final Query query = getDAOManager().createNativeQuery(
				SQLConstants.FIND_STUDENTS_WITH_STORY_DETAILS);
		query.setParameter("groupId", groupId);
		query.setFirstResult(0).setMaxResults(topStories);
		final ResultTransformer transformer = Transformers.aliasToBean(StudentPdfVO.class);
		daoUtil.applyTransformer(query, transformer);
		final List<StudentPdfVO> resultList = query.getResultList();		
		return resultList;
	}
	public Map<String, LinkedHashMap<String, Object>> getTopParticipantsData(
			final int groupId, final int topParticipants) {
		Map<String, LinkedHashMap<String, Object>> studentList = null;
		try {
			final Query query = getDAOManager().createNativeQuery(
						SQLConstants.LIST_ALL_TOP_PARTICIPATED_STUDENTS);	
			query.setParameter("groupId", groupId);
			query.setFirstResult(0).setMaxResults(topParticipants);
			final ResultTransformer transformer = Transformers
					.aliasToBean(StudentDetailsVO.class);
			daoUtil.applyTransformer(query, transformer);
			final List<StudentDetailsVO> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				studentList = getTopStudentDetails(results);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occurs at getTopParticipantsData", exception.getMessage());
		}
		return studentList;
	}
	private Map<String, LinkedHashMap<String, Object>> getTopStudentDetails(
			final List<StudentDetailsVO> results) {
		final LinkedHashMap<String, LinkedHashMap<String, Object>> returnStudentDetails = 
				new LinkedHashMap<String, LinkedHashMap<String, Object>>();
		iterateStudentInfo(returnStudentDetails, results);
		return returnStudentDetails;
	}
	private void iterateStudentInfo(
			final Map<String, LinkedHashMap<String, Object>> returnStudentDetails,
			final List<StudentDetailsVO> results) {
		for (final StudentDetailsVO result : results) {
			addSchoolInfo(result, returnStudentDetails);		
		}		
	}
	private void addSchoolInfo(final StudentDetailsVO result,
			final Map<String, LinkedHashMap<String, Object>> returnStudentDetails) {
		LinkedHashMap<String, Object> studentDetails = null;

		final Long studentId = result.getStudentId() == null ? 0 : result.getStudentId()
				.longValue();
		final Short age = result.getAge() == null ? Short.valueOf((short)0) : result.getAge();

		final String fullName = result.getFullName() == null ? "0" : result.getFullName();
		final String parentEmail = result.getParentEmail() == null ? "0" : result
				.getParentEmail();
		final String parentName = result.getParentName() == null ? "0" : result.getParentName();
		final String parentPhone = result.getParentPhone() == null ? "" : result
				.getParentPhone();

		final String regNo = result.getRegNo() == null ? "0" : result.getRegNo();

		final String className = result.getClassName() == null ? "0" : result.getClassName();
		final String classDesc = result.getClassDesc() == null ? "0" : result.getClassDesc();
		final String groupName = result.getGroupName() == null ? "0" : result.getGroupName();
		final String groupDesc = result.getGroupDesc() == null ? "0" : result.getGroupDesc();
		final String address = result.getAddress() == null ? "0" : result.getAddress();
		final String city = result.getCity() == null ? "0" : result.getCity();
		final String schoolName = result.getSchoolName() == null ? "0" : result.getSchoolName();
		final String phone = result.getPhone() == null ? "" : result.getPhone();
		final Integer pincode = result.getPincode() == null ? Integer.valueOf(0) : result.getPincode();
		final String principalEmail = result.getPrincipalEmail() == null ? "0" : result
				.getPrincipalEmail();
		final String principalName = result.getPrincipalName() == null ? "0" : result
				.getPrincipalName();
		final String state = result.getState() == null ? "0" : result.getState();
		final String topicName = result.getTopicName() == null ? "" : result
				.getTopicName();
		final String fullNameStudent = "\"" + fullName + parentEmail + "\"";
		final LinkedHashMap<String, Object> studentInfo = (LinkedHashMap<String, Object>) returnStudentDetails
				.get(fullNameStudent);
		if (studentInfo == null) {
			studentDetails = new LinkedHashMap<String, Object>();
			studentDetails.put("studentId", studentId);
			studentDetails.put("fullName", fullName);
			studentDetails.put("age", age);
			studentDetails.put("parentEmail", parentEmail);
			studentDetails.put("parentName", parentName);
			studentDetails.put("parentPhone", parentPhone);
			studentDetails.put("regNo", regNo);
			studentDetails.put("className", className);
			studentDetails.put("classDesc", classDesc);
			studentDetails.put("groupName", groupName);
			studentDetails.put("groupDesc", groupDesc);
			studentDetails.put("address", address);
			studentDetails.put("city", city);
			studentDetails.put("SchoolName", schoolName);
			studentDetails.put("phone", phone);
			studentDetails.put("pincode", pincode);
			studentDetails.put("principalEmail", principalEmail);
			studentDetails.put("principalName", principalName);
			studentDetails.put("state", state);
			studentDetails.put("topicName", topicName);
			returnStudentDetails.put(fullNameStudent, studentDetails);
		}
			
	}
	/**
	 * Retrieve list of story submitted students
	 * @return
	 */
	public Map<String, LinkedHashMap<String, Object>> getSubmittedStudents() {

		Map<String, LinkedHashMap<String, Object>> studentList = null;
		try {
			final Query query = getDAOManager().createNativeQuery(
					SQLConstants.LIST_ALL_STORY_SUBMITTED_STUDENTS);

			final ResultTransformer transformer = Transformers
					.aliasToBean(StudentDetailsVO.class);
			daoUtil.applyTransformer(query, transformer);
			final List<StudentDetailsVO> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				studentList = getStudentDetails(results);
			}
		} catch (Exception exception) {
			LOGGER.error(
					"Exception occurs while retriving list of story submitted students",
					exception.getMessage());
		}
		return studentList;
	}
}
