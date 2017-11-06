/**
 * 
 */
package com.scholastic.intl.writingawards.job;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.dao.impl.SchoolDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StoryDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StudentDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.TaskDaoImpl;
import com.scholastic.intl.writingawards.entity.School;
import com.scholastic.intl.writingawards.entity.Student;
import com.scholastic.intl.writingawards.entity.Task;
import com.scholastic.intl.writingawards.model.EmailMessageVO;
import com.scholastic.intl.writingawards.serviceImpl.EmailServiceImpl;

/**
 * @author madhusmita.nayak
 * 
 */
@Stateless
public class SendEmailTaskProcessor {
	private static final String TO_PRINCIPAL_TEMPLATE = "to_principal_school";
	private static final String RANKING_STORIES_TEMPLATE = "ranking_stories";
	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailTaskProcessor.class);

	@Inject
	TaskDaoImpl taskDAOImpl;
	@Inject
	SchoolDAOImpl schoolDAOImpl;
	@Inject
	StudentDAOImpl studentDAOImpl;
	@Inject
	EmailServiceImpl emailService;
	@Inject
	SendEmailJob sendEmail;
	@Inject
	StoryDAOImpl storyDAOImpl;
	private Boolean status = false;
	EmailMessageVO emailMessageVO = null;

	public void executeRegdSubmissionJob(final String taskType) {
		Task taskentity = taskDAOImpl.getTaskByDescriptiob(taskType);
		Days days = Days.daysBetween(new LocalDate(), new LocalDate(taskentity.getEndDate()));
		final int dateDifference = days.getDays();
		LOGGER.info("date diff " + dateDifference);
		Map<String, Object> globalParams = new HashMap<String, Object>();
		if (dateDifference == -1) {
			List<School> schools = schoolDAOImpl.getAllSchools();
			for (School school : schools) {
				emailMessageVO = new EmailMessageVO();
				emailMessageVO.setRecipientAddress(school.getPrincipalEmail());
				emailMessageVO.setTemplateName(TO_PRINCIPAL_TEMPLATE);
				emailMessageVO.setUserId(null);
				globalParams.put("schoolName", removeSchoolName(school.getName()));
				emailMessageVO.setGlobalParams(globalParams);
				emailMessageVO.setTestEmail(false);
				status = emailService.sendEmail(emailMessageVO);
				if (status) {
					sendEmail.execute();
				}
			}

		}
	}

	/**
	 * STROY RATING ASSIGNMENT
	 */
	public void executeStroyRatingJob(final String taskType) {
		final Task taskEntity = taskDAOImpl.getTaskByDescriptiob(taskType);
		Days days = Days.daysBetween(new LocalDate(), new LocalDate(taskEntity.getStartDate()));
		final int dateDifference = days.getDays();
		LOGGER.info("date diff " + dateDifference);
		Map<String, Object> globalParams = new HashMap<String, Object>();
		final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
		// Adding it outside the loop as formatting the date is not required
		// again and again for each students
		if (taskEntity.getEndDate() != null) {
			globalParams.put("rankingEndDate", dateFormatter.format(taskEntity.getEndDate()));
		}

		if (taskEntity.getStartDate() != null) {
			globalParams.put("rankingStartDate", dateFormatter.format(taskEntity.getStartDate()));
		}

		if (dateDifference == 2) {
			final List<Student> students = studentDAOImpl.getAllStudents();
			for (Student student : students) {
				emailMessageVO = new EmailMessageVO();
				emailMessageVO.setRecipientAddress(student.getParentEmail());
				emailMessageVO.setTemplateName(RANKING_STORIES_TEMPLATE);
				emailMessageVO.setUserId(null);
				globalParams.put("fullName", student.getFullName());
				emailMessageVO.setGlobalParams(globalParams);
				emailMessageVO.setTestEmail(false);
				status = emailService.sendEmail(emailMessageVO);
				if (status) {
					sendEmail.execute();
				}
			}

		}
	}

	public void executeStroyRatingMarkZeroJob(String storyRating) {
		Task taskEntity = taskDAOImpl.getTaskByDescriptiob(storyRating);
		Days days = Days.daysBetween(new LocalDate(), new LocalDate(taskEntity.getEndDate()));
		final int dateDifference = days.getDays();
		if (dateDifference == -1) {
			storyDAOImpl.updateStoryTotalMark();
		}
	}

	private String removeSchoolName(final String schoolName) {
		final String schoolPattern = "school";
		String newSchoolName = null;
		if (Pattern.compile(Pattern.quote(schoolPattern), Pattern.CASE_INSENSITIVE)
				.matcher(schoolName).find()) {
			newSchoolName = Pattern.compile(Pattern.quote(schoolPattern), Pattern.CASE_INSENSITIVE)
					.matcher(schoolName).replaceAll("");

		} else {
			return schoolName;
		}
		return newSchoolName;
	}

}
