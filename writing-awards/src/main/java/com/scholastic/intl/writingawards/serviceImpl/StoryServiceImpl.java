package com.scholastic.intl.writingawards.serviceImpl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.dao.impl.StoryDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StudentDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.TopicDAOImpl;
import com.scholastic.intl.writingawards.entity.Story;
import com.scholastic.intl.writingawards.entity.Student;
import com.scholastic.intl.writingawards.entity.Topic;
import com.scholastic.intl.writingawards.model.AuthUserVO;
import com.scholastic.intl.writingawards.model.EmailMessageVO;
import com.scholastic.intl.writingawards.model.StoryVO;
import com.scholastic.intl.writingawards.service.StoryService;

@Stateless
public class StoryServiceImpl implements StoryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StoryService.class);

	@Inject
	StoryDAOImpl storyDAOImpl;

	@Inject
	TopicDAOImpl topicDAOImpl;

	@Inject
	EmailServiceImpl emailServiceImpl;

	@Inject
	StudentDAOImpl studentDAOImpl;

	@Override
	public Boolean saveStory(StoryVO storyVO, AuthUserVO authUserVO) {
		LOGGER.info("Save Story serivce.");
		Boolean status = Boolean.FALSE;

		if (storyVO != null) {
			Topic topic = topicDAOImpl.getTopic(storyVO.getTopicId());

			if (topic == null) {

				LOGGER.info("Topic not found");

			}

			else {

				Boolean saved = Boolean.FALSE;
				Story story = null;
				Student student = studentDAOImpl
						.getStudentByEmail(authUserVO.getUser().getUserId());
				if (student != null) {
					story = storyDAOImpl.getStoryByStudent(student.getId());

					if (story == null) {
						story = new Story();
					} else {
						saved = Boolean.TRUE;
					}

					story.setTopic(topic);
					story.setTitle(storyVO.getStoryTitle());
					story.setStoryText(storyVO.getStoryText());

					story.setStudentId(student.getId());
					story.setTotalMarks(0);
					story.setDeleted(Boolean.FALSE);
					if (storyVO.getSubmit().equalsIgnoreCase("Submit")) {
						Date date = new Date();
						story.setSubmissionDt(date);

					}
					if (saved) {
						status = storyDAOImpl.updateStory(story);
					} else {
						status = storyDAOImpl.saveStory(story);

					}

					if (status && storyVO.getSubmit().equalsIgnoreCase("Submit")) {
						EmailMessageVO emailMessageVO = emailServiceImpl.setEmailMessageVO(
								authUserVO.getUser(), "story_submission");
						emailServiceImpl.sendEmail(emailMessageVO);

					}

				} else {

					LOGGER.info("Student ID not found for registered user");
					return status;
				}

			}
		}

		else {
			LOGGER.info("Invalid Story VO");
		}

		return status;

	}

	public Story getStory(Long id) {

		return storyDAOImpl.getStory(id);
	}

	@Override
	public Story getStoryByStudent(String studentId) {

		Student student = studentDAOImpl.getStudentByEmail(studentId);
		if (student != null) {
			// System.out.println("   student Id in studen " + studentId);
			return storyDAOImpl.getStoryByStudent(student.getId());
		}

		else {

			LOGGER.info("Student ID not found for registered user");
			return null;
		}

	}

}
