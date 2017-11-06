package com.scholastic.intl.writingawards.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.dao.impl.AssignmentDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.ReviewDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StoryDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StudentDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StudentDetailsDaoImpl;
import com.scholastic.intl.writingawards.entity.AppConfig;
import com.scholastic.intl.writingawards.entity.Question;
import com.scholastic.intl.writingawards.entity.Story;
import com.scholastic.intl.writingawards.entity.StoryAssignment;
import com.scholastic.intl.writingawards.entity.StoryReview;
import com.scholastic.intl.writingawards.entity.Student;
import com.scholastic.intl.writingawards.model.QuestionVO;
import com.scholastic.intl.writingawards.model.ReviewStoryVO;
import com.scholastic.intl.writingawards.model.ReviewVO;
import com.scholastic.intl.writingawards.service.ReviewService;

@Stateless
public class ReviewServiceImpl implements ReviewService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

	@Inject
	ReviewDAOImpl reviewDAOImpl;

	@Inject
	AssignmentDAOImpl assignmentDAOImpl;

	@Inject
	StudentDAOImpl studentDAOImpl;
	@Inject
	StoryDAOImpl storyDAOImpl;

	@Inject
	StudentDetailsDaoImpl studentDetailsDaoImpl;

	@Override
	public Boolean saveReview(ReviewVO reviewVO, String userId) {

		Boolean status = Boolean.FALSE;
		StoryReview storyReview;
		Integer totalMarks = 0;
		ArrayList<StoryReview> storyReviews = new ArrayList<StoryReview>();

		if (reviewVO != null && reviewVO.getQuestions() != null) {
			LOGGER.info(" Story Id passed " + reviewVO.getStoryId());
			StoryAssignment storyAssignment = getStoryAssignment(userId, reviewVO.getStoryId());

			for (QuestionVO questionVO : reviewVO.getQuestions()) {
				storyReview = new StoryReview();
				storyReview.setStoryAssignmentId(reviewVO.getStoryId());
				storyReview.setQuestionId(questionVO.getId());
				storyReview.setWeightage(questionVO.getWeightage());
				totalMarks += questionVO.getWeightage();

				storyReviews.add(storyReview);

			}

			status = reviewDAOImpl.saveReviews(storyReviews);
			if (status) {
				List<AppConfig> appConfigs = studentDetailsDaoImpl.getAppConfig();
				if (totalMarks == Integer.parseInt(appConfigs.get(5).getConfigValue())) {
					totalMarks += Integer.parseInt(appConfigs.get(6).getConfigValue());
				}

				storyAssignment.setTotalMarks(totalMarks);
				status = assignmentDAOImpl.updateAssignment(storyAssignment);
			}

			List<StoryAssignment> assignments = assignmentDAOImpl.getAssignmentsByStory(reviewVO
					.getStoryId());
			if (assignments != null && !assignments.isEmpty()) {
				int count = 0;
				totalMarks = 0;
				for (StoryAssignment sa : assignments) {
					if (sa.getTotalMarks() != null && sa.getTotalMarks() > 0) {
						totalMarks += sa.getTotalMarks();
						count++;
					}
				}

				if (count > 0) {
					totalMarks = totalMarks / count;

					Story story = storyDAOImpl.getStory(reviewVO.getStoryId());
					if (story != null) {
						story.setTotalMarks(totalMarks);
						status = storyDAOImpl.saveStory(story);
					}

				}

			}

		}
		return status;
	}

	@Override
	public List<ReviewStoryVO> getAssignments(String studentId) {
		LOGGER.info(" Student Id " + studentId);
		Student student = studentDAOImpl.getStudentByEmail(studentId);
		if (student != null)
			return assignmentDAOImpl.getAssignmentByStudent(student.getId());
		else
			return null;
	}

	public StoryAssignment getStoryAssignment(String studentId, Long storyId) {
		LOGGER.info(" Student Id " + studentId);
		Student student = studentDAOImpl.getStudentByEmail(studentId);
		if (student != null) {
			return assignmentDAOImpl.getAssignment(student.getId(), storyId);
		} else {
			return null;
		}
	}

	public List<Question> getAllQuestions() {
		return reviewDAOImpl.getAllQuestions();
	}
}