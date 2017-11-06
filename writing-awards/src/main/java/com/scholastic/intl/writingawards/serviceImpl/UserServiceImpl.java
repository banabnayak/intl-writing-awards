package com.scholastic.intl.writingawards.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.dao.impl.AssignmentDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.ReviewDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StoryDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StudentDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.TaskDaoImpl;
import com.scholastic.intl.writingawards.dao.impl.UserDAOImpl;
import com.scholastic.intl.writingawards.entity.Story;
import com.scholastic.intl.writingawards.entity.StoryAssignment;
import com.scholastic.intl.writingawards.entity.StoryReview;
import com.scholastic.intl.writingawards.entity.Student;
import com.scholastic.intl.writingawards.entity.Task;
import com.scholastic.intl.writingawards.entity.User;
import com.scholastic.intl.writingawards.helper.PasswordUtil;
import com.scholastic.intl.writingawards.model.AuthUserVO;
import com.scholastic.intl.writingawards.model.EmailMessageVO;
import com.scholastic.intl.writingawards.service.UserService;

@Stateless
@Default
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Inject
	UserDAOImpl userDAO;

	@Inject
	StoryDAOImpl storyDAOImpl;

	@Inject
	StudentDAOImpl studentDAOImpl;

	@Inject
	ReviewDAOImpl reviewDAOImpl;

	@Inject
	TaskDaoImpl taskDaoImpl;

	@Inject
	AssignmentDAOImpl assignmentDAOImpl;

	/**
	 * Email service layer
	 */
	@Inject
	EmailServiceImpl emailServiceImpl;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
	
	
	public Boolean checkRegistartionDate(){
		LOGGER.info("checking registaryion date");
		Task task = taskDaoImpl.getTaskById(1L);		
		if(task.getEndDate().compareTo(new Date()) < 0 ){
			LOGGER.info(" Registations Closed !!");
			return Boolean.TRUE;
		}
		else{
			LOGGER.info(" Registations Open ....");
			return Boolean.FALSE;
		}
		
	}

	@Override
	public void addUser(User user) {
		userDAO.save(user);

	}

	@Override
	public User getUser(Long id) {
		User user = userDAO.getUser(id);

		return user;
	}

	@Override
	public void removeUser(User user) {
		userDAO.save(user);

	}

	@Override
	public AuthUserVO authenticate(AuthUserVO authUserVO) {
		User user = userDAO.authenticate(authUserVO.getUser().getUserId(),
				authUserVO.getUser().getPassword());
		if (user != null
				&& user.getPassword()
						.equals(authUserVO.getUser().getPassword())) {			
			authUserVO.setUser(user);
			authUserVO.setLoginStatus(true);
		} else {
			authUserVO.setUser(null);
			authUserVO.setLoginStatus(false);
		}
		return authUserVO;
	}

	// @Override
	public AuthUserVO authenticate1(AuthUserVO authUserVO) {
		User user = userDAO.authenticate(authUserVO.getUser().getUserId(),
				authUserVO.getUser().getPassword());
		Task task = null;
		if (user != null) {

			authUserVO.setIsAnnouncementStarted(Boolean.FALSE);

			Story story = storyDAOImpl.getStoryByStudent(user.getId());
			if (story != null) {

				LOGGER.info("story Found");
				Date submissionDt = story.getSubmissionDt();
				if (submissionDt != null) {
					authUserVO.setIsSaved(Boolean.FALSE);
					authUserVO.setIsStorySubmitted(Boolean.TRUE);
					LOGGER.info("Story is submitted " + submissionDt);

					if (authUserVO.getIsStorySubmitted()) {

						LOGGER.info("finding Review date");
						task = taskDaoImpl.getTaskById(2L);

						if (task != null
								&& (task.getStartDate().compareTo(new Date()) <= 0)) {

							authUserVO.setIsReviewStarted(Boolean.TRUE);

							Student student = studentDAOImpl
									.getStudentByEmail(authUserVO.getUser()
											.getUserId());
							if (student != null) {
								List<StoryAssignment> assignments = assignmentDAOImpl
										.getAssignmentByStudent1(student
												.getId());
								authUserVO.setIsReviewed(Boolean.FALSE);
								for (StoryAssignment assignment : assignments) {
									authUserVO.setIsReviewed(Boolean.TRUE);
									List<StoryReview> reviews = reviewDAOImpl
											.getReviewsByAssignment(assignment
													.getId());
									if (reviews.size() >= 4) {
										continue;

									} else {
										authUserVO.setIsReviewed(Boolean.FALSE);
									}
								}
							} else {
								LOGGER.info("Student Details not for user ID"
										+ authUserVO.getUser().getUserId());
							}

						} else {
							authUserVO.setIsSaved(Boolean.FALSE);
							authUserVO.setIsStorySubmitted(Boolean.TRUE);
							authUserVO.setIsReviewStarted(Boolean.FALSE);
							authUserVO.setIsReviewed(Boolean.FALSE);

						}
					} else {
						authUserVO.setIsSaved(Boolean.TRUE);
						authUserVO.setIsStorySubmitted(Boolean.FALSE);
						authUserVO.setIsReviewed(Boolean.FALSE);
						authUserVO.setIsReviewStarted(Boolean.FALSE);
					}
					LOGGER.info("Story Save.");
					authUserVO.setIsSaved(Boolean.TRUE);
					authUserVO.setIsStorySubmitted(Boolean.FALSE);
					authUserVO.setIsReviewed(Boolean.FALSE);
					authUserVO.setIsReviewStarted(Boolean.FALSE);
				}

			} else {
				LOGGER.info("Story Not found");
				authUserVO.setIsSaved(Boolean.FALSE);
				authUserVO.setIsStorySubmitted(Boolean.FALSE);
				authUserVO.setIsReviewed(Boolean.FALSE);
				authUserVO.setIsReviewStarted(Boolean.FALSE);
				LOGGER.info("finding Submission date");
				task = taskDaoImpl.getTaskById(1L);

				Task task1 = new Task();
				task1 = taskDaoImpl.getTaskById(2L);
				if (task1 != null
						&& (task1.getStartDate().compareTo(new Date()) <= 0)) {

					authUserVO.setIsReviewStarted(Boolean.TRUE);
				}

				task1 = taskDaoImpl.getTaskById(3L);
				if (task1 != null
						&& (task1.getStartDate().compareTo(new Date()) <= 0)) {

					authUserVO.setIsAnnouncementStarted(Boolean.TRUE);
					authUserVO.setIsReviewStarted(Boolean.FALSE);
				}

			}

			if (task != null) {
				if (authUserVO.getIsStorySubmitted()
						&& task.getStartDate().compareTo(new Date()) <= 0) {
					authUserVO.setIsReviewStarted(Boolean.TRUE);
				}
				authUserVO.setStartDate(dateFormat.format(task.getStartDate()));
				authUserVO.setEndDate(dateFormat.format(task.getEndDate()));

			}

			User user1 = new User();
			user1.setUserId(user.getUserId());
			user1.setFullName(user.getFullName());
			user1.setRole(user.getRole());

			user1.setPassword("");
			authUserVO.setUser(user);
			// authUserVO.setIsReviewStarted(false);
			authUserVO.setUser(user);
			LOGGER.info(authUserVO.toString());
			return authUserVO;

		} else {

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("User Not Found");

			}
			return null;

		}

	}

	@Override
	public void storyActivities(AuthUserVO authUserVO) {
		authUserVO.setIsSaved(Boolean.FALSE);
		authUserVO.setIsStorySubmitted(Boolean.FALSE);
		authUserVO.setIsReviewed(Boolean.FALSE);
		authUserVO.setIsReviewStarted(Boolean.FALSE);
		authUserVO.setIsAnnouncementStarted(Boolean.FALSE);
		authUserVO.setIsReviewClosed(Boolean.FALSE);

		Task task = null;
		boolean checkForAnn = false;
		Student student = studentDAOImpl.getStudentByEmail(authUserVO.getUser()
				.getUserId());
		if (student != null) {
			Story story = storyDAOImpl.getStoryByStudent(student.getId());
			if (story != null) {
				LOGGER.info("story Found");
				Date submissionDt = story.getSubmissionDt();
				if (submissionDt != null) {
					authUserVO.setIsSaved(Boolean.TRUE);
					authUserVO.setIsStorySubmitted(Boolean.TRUE);
					LOGGER.info("Story is submitted " + submissionDt);
					LOGGER.info("finding Review date");
					task = taskDaoImpl.getTaskById(2L);
					if (task != null) {
						if(task.getEndDate().compareTo(new Date())< 0 ){
							authUserVO.setIsReviewClosed(Boolean.TRUE);
						}
						
						if (task.getStartDate().compareTo(new Date()) <= 0) {
							authUserVO.setIsReviewStarted(Boolean.TRUE);
							// Student student =
							// studentDAOImpl.getStudentByEmail(authUserVO.getUser().getUserId());
							List<StoryAssignment> assignments = assignmentDAOImpl
									.getAssignmentByStudent1(student.getId());
							authUserVO.setIsReviewed(Boolean.TRUE);
							for (StoryAssignment assignment : assignments) {
								// authUserVO.setIsReviewed(Boolean.TRUE);
								// List<StoryReview> reviews =
								// reviewDAOImpl.getReviewsByAssignment(assignment.getId());
								// LOGGER.info(" reviews started   "+
								// reviews.size());
								if (assignment.getTotalMarks() == 0
										|| assignment.getTotalMarks() == null) {
									authUserVO.setIsReviewed(Boolean.FALSE);
									break;
								}
							}
							checkForAnn = true;
						} else {
							checkForAnn = true;

						}
					} else {
						// no task for review
					}
				} else {
					checkForAnn = true;
					authUserVO.setIsSaved(Boolean.TRUE);

				}
			} else {
				checkForAnn = true;
			}

			if (checkForAnn) {
				LOGGER.info("Story Not found");
				Task task3 = taskDaoImpl.getTaskById(1L);
				Task task2 = null;

				if (authUserVO.getIsReviewed()
						|| task3.getEndDate().compareTo(new Date()) < 0
						&& (!authUserVO.getIsStorySubmitted())) {
					authUserVO.setIsAnnouncementStarted(Boolean.TRUE);
					task2 = taskDaoImpl.getTaskById(3L);
					if (task2 != null && task2.getStartDate() != null
							&& task2.getEndDate() != null) {

						authUserVO.setStartDate(dateFormat.format(task2
								.getStartDate()));
						authUserVO.setEndDate(dateFormat.format(task2
								.getEndDate()));
					} else {

						authUserVO.setStartDate(" ");
						authUserVO.setEndDate(" ");

					}

				}

				else {
					/*
					 * authUserVO.setIsSaved(Boolean.FALSE);
					 * authUserVO.setIsStorySubmitted(Boolean.FALSE);
					 * authUserVO.setIsReviewed(Boolean.FALSE);
					 * authUserVO.setIsReviewStarted(Boolean.FALSE);
					 * authUserVO.setIsAnnouncementStarted(Boolean.FALSE);
					 */LOGGER.info("finding Submission date");

					Task task1 = taskDaoImpl.getTaskById(2L); // review date
					
					if (task1 != null
							&& (task1.getStartDate().compareTo(new Date()) <= 0)) {
						authUserVO.setIsReviewStarted(Boolean.TRUE);
						authUserVO.setStartDate(dateFormat.format(task1
								.getStartDate()));
						authUserVO.setEndDate(dateFormat.format(task1
								.getEndDate()));
						task2 = taskDaoImpl.getTaskById(3L); // announcement
																// date
						if (task2 != null
								&& (task2.getStartDate().compareTo(new Date()) < 0)) {
							authUserVO.setIsAnnouncementStarted(Boolean.TRUE);
							authUserVO.setIsReviewStarted(Boolean.TRUE);
							authUserVO.setStartDate(dateFormat.format(task2
									.getStartDate()));
							authUserVO.setStartDate(dateFormat.format(task2
									.getEndDate()));
						}
					} else {

						// task3 = taskDaoImpl.getTaskById(1L); //submission
						// date
						if (task3.getEndDate().compareTo(new Date()) < 0
								&& !authUserVO.getIsStorySubmitted()) {
							authUserVO.setIsAnnouncementStarted(Boolean.TRUE);
						}

						if (authUserVO.getIsStorySubmitted()
								&& !authUserVO.getIsReviewStarted()
								&& task1 != null) {
							LOGGER.info("  Setting review dates "
									+ task1.getStartDate() + " "
									+ task1.getEndDate());
							authUserVO.setStartDate(dateFormat.format(task1
									.getStartDate()));
							authUserVO.setEndDate(dateFormat.format(task1
									.getEndDate()));
							return;

						}

						if (authUserVO.getIsReviewed()
								|| (!authUserVO.getIsStorySubmitted() && authUserVO
										.getIsAnnouncementStarted())) {
							task2 = taskDaoImpl.getTaskById(3L);
							if (task2 != null && task2.getStartDate() != null
									&& task2.getEndDate() != null) {
								authUserVO.setStartDate(dateFormat.format(task2
										.getStartDate()));
								authUserVO.setEndDate(dateFormat.format(task2
										.getEndDate()));
							} else {

								authUserVO.setStartDate(" ");
								authUserVO.setEndDate(" ");

							}

						} else {
							authUserVO.setStartDate(dateFormat.format(task3
									.getStartDate()));
							authUserVO.setEndDate(dateFormat.format(task3
									.getEndDate()));
						}

					}
				}
			}

		}
		LOGGER.info(authUserVO.toString());
	}

	public void storyActivities1(AuthUserVO authUserVO) {
		Student student = studentDAOImpl.getStudentByEmail(authUserVO.getUser()
				.getUserId());
		if (student != null) {
			Story story = storyDAOImpl.getStoryByStudent(student.getId());
			if (story != null) {
				Date submissionDt = story.getSubmissionDt();
				if (submissionDt != null) {
					authUserVO.setIsStorySubmitted(Boolean.TRUE);
				} else {
					authUserVO.setIsSaved(Boolean.TRUE);
					authUserVO.setIsStorySubmitted(Boolean.FALSE);
				}
				authUserVO.setIsReviewed(Boolean.FALSE);
			} else {
				authUserVO.setIsSaved(Boolean.FALSE);
				authUserVO.setIsReviewStarted(Boolean.FALSE);
				authUserVO.setIsReviewed(Boolean.FALSE);
				authUserVO.setIsStorySubmitted(Boolean.FALSE);
			}
			Task task = null;
			if (!authUserVO.getIsStorySubmitted()) {
				LOGGER.info("finding Submission Status");
				task = taskDaoImpl.getTaskById(1L);
			} else {

				LOGGER.info("finding Review Status");
				// Student student =
				// studentDAOImpl.getStudentByEmail(authUserVO.getUser().getUserId());

				List<StoryAssignment> assignments = assignmentDAOImpl
						.getAssignmentByStudent1(student.getId());
				authUserVO.setIsReviewed(Boolean.FALSE);
				LOGGER.info("Assignments " + assignments.size());
				for (StoryAssignment assignment : assignments) {
					authUserVO.setIsReviewed(Boolean.TRUE);
					List<StoryReview> reviews = reviewDAOImpl
							.getReviewsByAssignment(assignment.getId());
					if (reviews.size() >= 4) {
						continue;

					} else {
						authUserVO.setIsReviewed(Boolean.FALSE);
					}
				}
			}
			if (authUserVO.getIsReviewed()) {

				LOGGER.info(" Review Done ");
				task = taskDaoImpl.getTaskById(3L);
				if (task != null
						&& (task.getStartDate().compareTo(new Date()) < 0)) {
					authUserVO.setIsAnnouncementStarted(Boolean.TRUE);
					authUserVO.setIsReviewStarted(Boolean.FALSE);

				}

			} else {
				LOGGER.info(" Review Pendin ");
				task = taskDaoImpl.getTaskById(2L);
			}
			if (task != null) {
				authUserVO.setStartDate(dateFormat.format(task.getStartDate()));
				authUserVO.setEndDate(dateFormat.format(task.getEndDate()));

			}
		}
	}

	@Override
	public User resetPassword(String regNo, String email) {

		User user = null;
		final Student student = studentDAOImpl.getStudentByReg(regNo);

		if (student != null) {
			try{
			if (student.getParentEmail().equalsIgnoreCase(email)) {
				user = userDAO.findUserwithEmail(email);				
			if (user != null){				
				user.setPassword(PasswordUtil.getPassword());
			userDAO.update(user);
			final EmailMessageVO emailMessageVO = setEmailMessageVO(user);
			emailServiceImpl.sendEmail(emailMessageVO);
			}
			}
			}
			catch(Exception e){
				user = null;
			}
		}
		return user; 

	}

	private EmailMessageVO setEmailMessageVO(User user) {
		EmailMessageVO emailMessageVO = new EmailMessageVO();
		// emailMessageVO.setUserId(user.getId());
		emailMessageVO.setRecipientAddress(user.getUserId());
		Map<String, Object> globalParams = new HashMap<String, Object>();
		globalParams.put("fullName", user.getFullName());
		globalParams.put("parentEmailId", user.getUserId());
		globalParams.put("oldPassword", user.getPassword());
		emailMessageVO.setGlobalParams(globalParams);
		emailMessageVO.setTestEmail(Boolean.FALSE);
		emailMessageVO.setTemplateName("forgot_password");

		// emailMessageVO.setUserId(user.getUserId());
		return emailMessageVO;

	}

}
