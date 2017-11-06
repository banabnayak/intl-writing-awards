package com.scholastic.intl.writingawards.serviceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.constants.SWAConstants;
import com.scholastic.intl.writingawards.dao.impl.SchoolDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StudentDetailsDaoImpl;
import com.scholastic.intl.writingawards.entity.AppConfig;
import com.scholastic.intl.writingawards.helper.ServiceHelper;
import com.scholastic.intl.writingawards.model.AppConfigVO;
import com.scholastic.intl.writingawards.model.StudentPdfVO;
import com.scholastic.intl.writingawards.service.AdminReportsService;

/**
 * @author madhusmita.nayak
 * 
 */
public class AdminReportServiceImpl implements AdminReportsService {
	/**
	 * LOGGER for the current class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminReportServiceImpl.class);
	private static final String NEW_LINE = "\n";
	private static final char QUOTE_STRING = '"';
	@Inject
	private StudentDetailsDaoImpl appConfigDaoImpl;
	
	@Inject
	private SchoolDAOImpl schoolDAOImpl;
	
	@Inject
	private ServiceHelper serviceHelper;

	@Override
	public List<AppConfigVO> topStoryLookUp() {
		final List<AppConfigVO> configList = appConfig(
				SWAConstants.TOP_STORIES, SWAConstants.STORY_ELEMENTS);
		return configList;
	}
	private StringBuilder getCSVFormat(
		final Map<String, LinkedHashMap<String, Object>> studentMap, final Boolean addTopicColumn) {
		final StringBuilder csvStrBuilder = new StringBuilder(",Student details,");
		final StringBuilder stringBuilderHeader = new StringBuilder();
		stringBuilderHeader
		.append("Student Name, Student Id, Student Age, Parent Email, "
				+ "Parent Name, Parent Phone, Student RegNo, Class Name, Class Desc, " 
				+ "Student Group Name, Group Desc, Address, City, School Name, Phone, " 
				+ "Pincode, Principal Email, Principal Name, State");
		if(addTopicColumn){
			stringBuilderHeader.append(", Topic Name");
		}
		csvStrBuilder.append(NEW_LINE);
		csvStrBuilder.append(stringBuilderHeader);
		csvStrBuilder.append(NEW_LINE);

		LinkedHashMap<String, Object> studentsMap = null;
		for (final Map.Entry<String, LinkedHashMap<String, Object>> studentEntry : studentMap
				.entrySet()) {			
			studentsMap = studentEntry.getValue();
			csvStrBuilder.append(getStudents(studentsMap, addTopicColumn));
			csvStrBuilder.append(NEW_LINE);
		}
		return csvStrBuilder;
	}

	/**
	 * method will returns the String Buffer
	 * @param addTopicColumn 
	 */
	private StringBuilder getStudents(final Map<String, Object> totUsageOfProduct, final Boolean addTopicColumn) {
		final StringBuilder csvStrBuilder = new StringBuilder();
		for(final Entry<String, Object> entry: totUsageOfProduct.entrySet()){
			entry.setValue(addQuote(entry.getValue().toString()));
        }
		final String studentId = totUsageOfProduct.get("studentId").toString();
		final String studentName = totUsageOfProduct.get("fullName").toString();
		final String age = totUsageOfProduct.get("age").toString();
		final String parentEmail = totUsageOfProduct.get("parentEmail").toString();
		final String parentName = totUsageOfProduct.get("parentName").toString();
		final String parentPhone = totUsageOfProduct.get("parentPhone").toString();
		final String regNo = totUsageOfProduct.get("regNo").toString();
		final String className = totUsageOfProduct.get("className").toString();
		final String classDesc = totUsageOfProduct.get("classDesc").toString();
		final String groupName = totUsageOfProduct.get("groupName").toString();
		final String groupDesc = totUsageOfProduct.get("groupDesc").toString();
		final String address = totUsageOfProduct.get("address").toString();
		final String city = totUsageOfProduct.get("city").toString();
		final String schoolName = totUsageOfProduct.get("SchoolName").toString();
		final String phone = totUsageOfProduct.get("phone").toString();
		final String pincode = totUsageOfProduct.get("pincode").toString();
		final String principalEmail = totUsageOfProduct.get("principalEmail").toString();
		final String principalName = totUsageOfProduct.get("principalName").toString();
		final String state = totUsageOfProduct.get("state").toString();
		final String topicName = totUsageOfProduct.get("topicName").toString();
		
		csvStrBuilder.append(studentName + "," + studentId + "," + age + "," + parentEmail + "," + parentName
				+ "," + parentPhone + "," + regNo + "," + className + "," + classDesc + ","
				+ groupName + "," + groupDesc + "," + address + "," + city + "," + schoolName + ","
				+ phone + "," + pincode + "," + principalEmail + "," + principalName + "," + state);
		
		if(addTopicColumn){
			csvStrBuilder.append("," + topicName + ",,");
		}else{
			csvStrBuilder.append(",,");
		}
		return csvStrBuilder;
	}

	private String addQuote(final String inputString) {
		String returnVal = "";
		if(inputString != null){
			returnVal = QUOTE_STRING + inputString + QUOTE_STRING;
		}
		return returnVal;
	}

	@Override
	public StringBuilder getStudentDetails() {
		StringBuilder sbStudentDetails = null;
		try {
			final HashMap<String, LinkedHashMap<String, Object>> studentList = 
					(HashMap<String, LinkedHashMap<String, Object>>) appConfigDaoImpl.retriveStudentDetails();
			if (studentList != null && !studentList.isEmpty()) {
				sbStudentDetails = getCSVFormat(studentList, Boolean.FALSE);
			}
		} catch (Exception exception) {
			LOGGER.error("exception occurs at getStudentDetails method ", exception.getMessage());
			throw exception;
		}
		return sbStudentDetails;
	}
	
	@Override
	public File createPdf4Stories(final int topStories, final int groupId) {
		final List<StudentPdfVO> pfdVOList = appConfigDaoImpl.createPdfVOList(topStories, groupId);
		if (pfdVOList == null || pfdVOList.isEmpty()) {
			return null;
		}
		final String tempDir = System.getProperty("java.io.tmpdir");
		final File tempFile = new File(tempDir, String.valueOf(System.currentTimeMillis()));
		LOGGER.info(tempDir);
		LOGGER.info(tempFile.getAbsolutePath());
		if (!tempFile.isDirectory()) {
			tempFile.mkdir();
		}
		for (final StudentPdfVO studentPdfVO : pfdVOList) {
			serviceHelper.pfdCreateUtility(tempFile.getAbsolutePath(), studentPdfVO);
		}
		return tempFile;
	}
	private List<AppConfigVO> appConfig(final String lookupName, final String lookupElements) {
		List<AppConfigVO> configList = null;
		int iteration = 0;
		int totalSize = 0;
		final List<AppConfig> appConfigList = appConfigDaoImpl.getAppConfig();
		if (appConfigList != null && !appConfigList.isEmpty()) {
			configList = new ArrayList<AppConfigVO>();
			AppConfigVO appConfigVO = null;
			for (final AppConfig appConfigObj : appConfigList) {
				if (lookupName.equalsIgnoreCase(
						appConfigObj.getDescription())) {
					totalSize = Integer.parseInt(appConfigObj.getConfigValue());
				} else if (lookupElements.equalsIgnoreCase(
						appConfigObj.getDescription())) {
					iteration = Integer.parseInt(appConfigObj.getConfigValue());
				}
				if (totalSize > 0 && iteration > 0) {
					break;
				}
			}
			for (int index = 0; index < iteration; index++) {
				appConfigVO = new AppConfigVO();
				if(SWAConstants.TOP_PARTICIPANTS.equalsIgnoreCase(lookupName)){
					appConfigVO.setTopParticipants(totalSize);
				}else{
					appConfigVO.setTopStories(totalSize);
				}
				totalSize = totalSize * 2;
				configList.add(appConfigVO);
			}
		}
		return configList;
	}

	@Override
	public List<AppConfigVO> topParticipantsLookup() {
		final List<AppConfigVO> configList = appConfig(
				SWAConstants.TOP_PARTICIPANTS,
				SWAConstants.PARTICIPANTS_ELEMENTS);
		return configList;
	}	
	@Override
	public StringBuilder getSchoolReport(final boolean isParticipated,
			final boolean isNetworkSchool) {
		StringBuilder sbNetworkSchool = null;
		try {
			final HashMap<String, LinkedHashMap<String, Object>> networkSchool = 
					(HashMap<String, LinkedHashMap<String, Object>>) schoolDAOImpl
					.getSchoolReport(isParticipated, isNetworkSchool);
			if (networkSchool != null && !networkSchool.isEmpty()) {
				sbNetworkSchool = getSchoolCsvFormat(networkSchool);
			}
		} catch (Exception exception) {
			LOGGER.error("exception occurs at getSchoolReport method ",
					exception.getMessage());
			throw exception;
		}
		return sbNetworkSchool;
	}

	private StringBuilder getSchoolCsvFormat(
			final HashMap<String, LinkedHashMap<String, Object>> schholMap) {
		final StringBuilder csvStrBuilder = new StringBuilder(",School details,");
		final StringBuilder stringBuilderHeader = new StringBuilder();
		stringBuilderHeader.append("School Name, Address, City, Phone, Pincode, "
				+ "Principal Email, Principal Name, State");
		csvStrBuilder.append(NEW_LINE);
		csvStrBuilder.append(stringBuilderHeader);
		csvStrBuilder.append(NEW_LINE);

		LinkedHashMap<String, Object> schoolsMap = null;
		for (final Map.Entry<String, LinkedHashMap<String, Object>> studentEntry : schholMap
				.entrySet()) {
			schoolsMap = studentEntry.getValue();
			csvStrBuilder.append(getSchoolReport(schoolsMap));
			csvStrBuilder.append(NEW_LINE);
		}
		return csvStrBuilder;
	}
	
	/**
	 * method will returns the String Buffer
	 */
	private StringBuilder getSchoolReport(
			final Map<String, Object> totUsageOfProduct) {
		final StringBuilder csvStrBuilder = new StringBuilder();		
		for(final Entry<String, Object> entry: totUsageOfProduct.entrySet()){
			entry.setValue(addQuote(entry.getValue().toString()));
        }
		final String schoolName = totUsageOfProduct.get("name")
				.toString();
		final String address = totUsageOfProduct.get("address")
				.toString();
		final String city = totUsageOfProduct.get("city").toString();

		final String phone = totUsageOfProduct.get("phone").toString();
		final String pinCode = totUsageOfProduct.get("pincode")
				.toString();
		final String principalEmail = totUsageOfProduct.get(
				"principalEmail").toString();
		final String principalName = totUsageOfProduct.get(
				"principalName").toString();
		final String state = totUsageOfProduct.get("state").toString();
		
		csvStrBuilder.append(schoolName + "," + address + "," + city + "," + phone + ","
				+ pinCode + "," + principalEmail + "," + principalName + ","
				+ state + ",,");
		return csvStrBuilder;
	}
	@Override
	public StringBuilder getSubmittedReport() {
		StringBuilder submittedParticipants = null;
		try {
			final HashMap<String, LinkedHashMap<String, Object>> participants = 
					(HashMap<String, LinkedHashMap<String, Object>>) appConfigDaoImpl
					.getSubmittedStudents();
			if (participants != null && !participants.isEmpty()) {
				submittedParticipants = getCSVFormat(participants, Boolean.TRUE);
			}
		} catch (Exception exception) {
			LOGGER.error("exception occurs at getSubmittedReport method ", exception.getMessage());
			throw exception;
		}
		return submittedParticipants;
	}
	@Override
	public StringBuilder getTopParticipantsData(final int groupId,
			final int topParticipants) {
		StringBuilder submittedParticipants = null;
		try {
			final LinkedHashMap<String, LinkedHashMap<String, Object>> participants = 
					(LinkedHashMap<String, LinkedHashMap<String, Object>>) appConfigDaoImpl
					.getTopParticipantsData(groupId, topParticipants);
			if (participants != null && !participants.isEmpty()) {
				submittedParticipants = getCSVFormat(participants, Boolean.TRUE);
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occurs at getTopParticipantsData method",
					exception.getMessage());
			throw exception;
		}
		return submittedParticipants;
	}
	@Override
	public StringBuilder getAllSchoolDetails() {
		StringBuilder sbSchoolDetails = null;
		try {
			final LinkedHashMap<String, LinkedHashMap<String, Object>> schoolList = 
					(LinkedHashMap<String, LinkedHashMap<String, Object>>) schoolDAOImpl.retriveAllSchoolDetails();
			if (schoolList != null && !schoolList.isEmpty()) {
				sbSchoolDetails = getSchoolCSVFormat(schoolList);
			}
		} catch (Exception exception) {
			LOGGER.error("exception occurs at getAllSchoolDetails method ", exception.getMessage());
			throw exception;
		}
		return sbSchoolDetails;
	}

	private StringBuilder getSchoolCSVFormat(
			final LinkedHashMap<String, LinkedHashMap<String, Object>> schoolInputMap) {
		final StringBuilder csvStrBuilder = new StringBuilder(
				",All School details,");
		final StringBuilder stringBuilderHeader = new StringBuilder();
		stringBuilderHeader
		.append("School Name, Phone, Pincode, Principal Email, Principal Name,"
				+ " Address, City, State, Class Name, Class Desc, Student Group Name, Group Desc,"
				+ " Student Name, Student Id, Student RegNo, Student Age, Parent Email, Parent Name,"
				+ " Parent Phone");
		csvStrBuilder.append(NEW_LINE);
		csvStrBuilder.append(stringBuilderHeader);
		csvStrBuilder.append(NEW_LINE);

		LinkedHashMap<String, Object> schoolMap = null;
		for (final Map.Entry<String, LinkedHashMap<String, Object>> schoolEntry : schoolInputMap
				.entrySet()) {
			schoolMap = schoolEntry.getValue();
			csvStrBuilder.append(getAllSchoolData(schoolMap));
			csvStrBuilder.append(NEW_LINE);
		}
		return csvStrBuilder;
	}
	private StringBuilder getAllSchoolData(final LinkedHashMap<String, Object> totUsageOfProduct) {
		final StringBuilder csvStrBuilder = new StringBuilder();
		for(final Entry<String, Object> entry: totUsageOfProduct.entrySet()){
			entry.setValue(addQuote(entry.getValue().toString()));
        }
		final String studentId = totUsageOfProduct.get("studentId").toString();
		final String age = totUsageOfProduct.get("age").toString();
		final String parentEmail = totUsageOfProduct.get("parentEmail").toString();
		final String parentName = totUsageOfProduct.get("parentName").toString();
		final String parentPhone = totUsageOfProduct.get("parentPhone").toString();
		final String regNo = totUsageOfProduct.get("regNo").toString();
		final String className = totUsageOfProduct.get("className").toString();
		final String classDesc = totUsageOfProduct.get("classDesc").toString();
		final String groupName = totUsageOfProduct.get("groupName").toString();
		final String groupDesc = totUsageOfProduct.get("groupDesc").toString();
		final String address = totUsageOfProduct.get("address").toString();
		final String city = totUsageOfProduct.get("city").toString();
		final String studentName = totUsageOfProduct.get("fullName").toString();
		final String phone = totUsageOfProduct.get("phone").toString();
		final String pincode = totUsageOfProduct.get("pincode").toString();
		final String principalEmail = totUsageOfProduct.get("principalEmail").toString();
		final String principalName = totUsageOfProduct.get("principalName").toString();
		final String state = totUsageOfProduct.get("state").toString();
		final String schoolName = totUsageOfProduct.get("SchoolName").toString();

		csvStrBuilder.append(schoolName + "," + phone + "," + pincode + "," + principalEmail + "," + principalName
				+ "," + address + "," + city + "," + state + "," + className + "," + classDesc + ","
				+ groupName + "," + groupDesc + "," + studentName + "," + studentId + "," + regNo + ","
				+ age + "," + parentEmail + "," + parentName + "," + parentPhone + ",,");

		return csvStrBuilder;
	}
	
}
