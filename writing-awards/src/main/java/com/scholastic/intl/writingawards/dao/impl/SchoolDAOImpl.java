package com.scholastic.intl.writingawards.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.School;
import com.scholastic.intl.writingawards.job.SQLConstants;
import com.scholastic.intl.writingawards.model.SchoolReportVO;
import com.scholastic.intl.writingawards.model.StudentDetailsVO;

@Named
public class SchoolDAOImpl extends GenericDAOSupport<School, Long> {

	private static final long serialVersionUID = 1L;
	/**
	 * LOGGER for the current class
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SchoolDAOImpl.class);

	@Inject
	private DAOUtilty daoUtil;

	/***
	 * 
	 * @param id
	 * @return
	 */
	public School getSchool(Long id) {
		final School school = getDAOManager().find(School.class, id);
		return school;
	}

	/***
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<School> getSchools(String name) {
		final Query query = getDAOManager().createNamedQuery("findSchool",
				School.class);
		final List<School> results = (ArrayList<School>) query.getResultList();
		return results;
	}

	/***
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public School getSchoolByPrincipalEmail(String principalEmail) {
		final Query query = getDAOManager().createNamedQuery(
				"findSchoolByEmail", School.class);
		query.setParameter("name", principalEmail);
		final List<School> results = (ArrayList<School>) query.getResultList();
		if (results != null && !results.isEmpty()) {
			return results.get(0);
		}
		return null;
	}

	public List<School> getAllSchools() {
		final Query query = getDAOManager().createNamedQuery("findSchool",
				School.class);
		final List<School> results = (ArrayList<School>) query.getResultList();
		return results;
	}

	/**
	 * Retrive school details
	 * 
	 * @param isParticipated
	 * @param isNetworkSchool
	 * @return
	 */
	public Map<String, LinkedHashMap<String, Object>> getSchoolReport(
			final boolean isParticipated, final boolean isNetworkSchool) {
		Query query = null;
		Map<String, LinkedHashMap<String, Object>> schoolReportList = null;
		try {
			if (!isNetworkSchool) {
				query = getDAOManager().createNativeQuery(
						SQLConstants.LIST_NON_NETWORK_SCHOOL_PARTICIPATED);
			} else {
				if (isParticipated) {
					query = getDAOManager().createNativeQuery(
							SQLConstants.LIST_NETWORK_SCHOOL_PARTICIPATED);
				} else {
					query = getDAOManager().createNativeQuery(
							SQLConstants.LIST_NETWORK_SCHOOL_NOT_PARTICIPATED);
				}
			}
			final ResultTransformer transformer = Transformers
					.aliasToBean(SchoolReportVO.class);
			daoUtil.applyTransformer(query, transformer);
			final List<SchoolReportVO> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				schoolReportList = getSchoolReport(results);
			}
			LOGGER.info("list of schools retrieved successfully");
		} catch (Exception exception) {
			LOGGER.error("Exception occurs while retriving list of schools",
					exception.getMessage());
			throw exception;
		}
		return schoolReportList;
	}

	private Map<String, LinkedHashMap<String, Object>> getSchoolReport(
			final List<SchoolReportVO> results) {
		LinkedHashMap<String, Object> schoolDetails = null;
		final Map<String, LinkedHashMap<String, Object>> returnSchoolInfo = 
				new HashMap<String, LinkedHashMap<String, Object>>();
		for (final SchoolReportVO result : results) {
			final String name = result.getName() == null ? "" : result
					.getName();
			final String address = result.getAddress() == null ? "" : result
					.getAddress();
			final String city = result.getCity() == null ? "" : result
					.getCity();
			final String phone = result.getPhone() == null ? "" : result
					.getPhone();
			final Integer pincode = result.getPincode() == null ? Integer
					.valueOf(0) : result.getPincode();
			final String principalEmail = result.getPrincipalEmail() == null ? ""
					: result.getPrincipalEmail();
			final String principalName = result.getPrincipalName() == null ? ""
					: result.getPrincipalName();
			final String state = result.getState() == null ? "" : result
					.getState();
			final String schoolName = "\"" + name + address + "\"";
			
			schoolDetails = (LinkedHashMap<String, Object>) returnSchoolInfo
					.get(schoolName);
			if (schoolDetails == null) {
				schoolDetails = new LinkedHashMap<String, Object>();
				schoolDetails.put("name", name);
				schoolDetails.put("address", address);
				schoolDetails.put("city", city);
				schoolDetails.put("phone", phone);
				schoolDetails.put("pincode", pincode);
				schoolDetails.put("principalEmail", principalEmail);
				schoolDetails.put("principalName", principalName);
				schoolDetails.put("state", state);
				returnSchoolInfo.put(schoolName, schoolDetails);
			}
		}
		return returnSchoolInfo;
	}

	public Map<String, LinkedHashMap<String, Object>> retriveAllSchoolDetails() {
		Map<String, LinkedHashMap<String, Object>> schoolList = null;
		try {
			final Query query = getDAOManager().createNativeQuery(
					SQLConstants.GET_ALL_SCHOOL_DETAILS);
			final ResultTransformer transformer = Transformers
					.aliasToBean(StudentDetailsVO.class);
			daoUtil.applyTransformer(query, transformer);
			final List<StudentDetailsVO> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				schoolList = getAllSchoolDetails(results);
			}
			LOGGER.info("All school details retrieved successfully");
		} catch (Exception exception) {
			LOGGER.error("Exception occurs at retriveAllSchoolDetails method",
					exception.getMessage());
			throw exception;
		}
		return schoolList;
	}

	private Map<String, LinkedHashMap<String, Object>> getAllSchoolDetails(
			final List<StudentDetailsVO> results) {
		final LinkedHashMap<String, LinkedHashMap<String, Object>> returnAllSchoolDetails = 
				new LinkedHashMap<String, LinkedHashMap<String, Object>>();
		iterateSchoolInfo(returnAllSchoolDetails, results);
		return returnAllSchoolDetails;
	}

	private void iterateSchoolInfo(
			final LinkedHashMap<String, LinkedHashMap<String, Object>> returnSchoolDetails,
			final List<StudentDetailsVO> results) {
		for (final StudentDetailsVO result : results) {
			addSchoolInfo(result, returnSchoolDetails);
		}
	}

	private void addSchoolInfo(
			final StudentDetailsVO result,
			final LinkedHashMap<String, LinkedHashMap<String, Object>> returnSchoolDetails) {
		LinkedHashMap<String, Object> schoolDetails = null;

		final Long studentId = result.getStudentId() == null ? 0 : result
				.getStudentId().longValue();
		final Short age = result.getAge() == null ? Short.valueOf((short) 0)
				: result.getAge();

		final String fullName = result.getFullName() == null ? "" : result
				.getFullName();
		final String parentEmail = result.getParentEmail() == null ? ""
				: result.getParentEmail();
		final String parentName = result.getParentName() == null ? "" : result
				.getParentName();
		final String parentPhone = result.getParentPhone() == null ? ""
				: result.getParentPhone();

		final String regNo = result.getRegNo() == null ? "" : result.getRegNo();

		final String className = result.getClassName() == null ? "" : result
				.getClassName();
		final String classDesc = result.getClassDesc() == null ? "" : result
				.getClassDesc();
		final String groupName = result.getGroupName() == null ? "" : result
				.getGroupName();
		final String groupDesc = result.getGroupDesc() == null ? "" : result
				.getGroupDesc();
		final String address = result.getAddress() == null ? "" : result
				.getAddress();
		final String city = result.getCity() == null ? "" : result.getCity();
		final String schoolName = result.getSchoolName() == null ? "" : result
				.getSchoolName();
		final String phone = result.getPhone() == null ? "" : result.getPhone();
		final Integer pincode = result.getPincode() == null ? Integer
				.valueOf(0) : result.getPincode();
		final String principalEmail = result.getPrincipalEmail() == null ? ""
				: result.getPrincipalEmail();
		final String principalName = result.getPrincipalName() == null ? ""
				: result.getPrincipalName();
		final String state = result.getState() == null ? "" : result.getState();

		final String schoolNameDetail = "\"" + schoolName + regNo + "\"";
		final LinkedHashMap<String, Object> schoolInfo = 
				(LinkedHashMap<String, Object>) returnSchoolDetails.get(schoolNameDetail);
		if (schoolInfo == null) {
			schoolDetails = new LinkedHashMap<String, Object>();
			schoolDetails.put("studentId", studentId);
			schoolDetails.put("fullName", fullName);
			schoolDetails.put("age", age);
			schoolDetails.put("parentEmail", parentEmail);
			schoolDetails.put("parentName", parentName);
			schoolDetails.put("parentPhone", parentPhone);
			schoolDetails.put("regNo", regNo);
			schoolDetails.put("className", className);
			schoolDetails.put("classDesc", classDesc);
			schoolDetails.put("groupName", groupName);
			schoolDetails.put("groupDesc", groupDesc);
			schoolDetails.put("address", address);
			schoolDetails.put("city", city);
			schoolDetails.put("phone", phone);
			schoolDetails.put("pincode", pincode);
			schoolDetails.put("principalEmail", principalEmail);
			schoolDetails.put("principalName", principalName);
			schoolDetails.put("state", state);
			schoolDetails.put("SchoolName", schoolName);
			returnSchoolDetails.put(schoolNameDetail, schoolDetails);
		}
	}
}
