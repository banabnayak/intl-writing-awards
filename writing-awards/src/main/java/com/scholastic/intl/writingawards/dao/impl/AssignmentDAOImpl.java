package com.scholastic.intl.writingawards.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.Story;
import com.scholastic.intl.writingawards.entity.StoryAssignment;
import com.scholastic.intl.writingawards.job.SQLConstants;
import com.scholastic.intl.writingawards.model.ReviewStoryVO;

public class AssignmentDAOImpl extends GenericDAOSupport<StoryAssignment, Serializable> {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentDAOImpl.class);

	@Inject
	DAOUtilty daoUtil;

	public Boolean saveAssignment(StoryAssignment storyAssignment) {

		Boolean status = Boolean.FALSE;

		try {
			save(storyAssignment);
			if (storyAssignment.getId() != null) {
				status = Boolean.TRUE;
				LOGGER.info("story assignment saved");
			}
		} catch (Exception e) {
			LOGGER.info("assignment save failed " + e);
		}

		return status;

	}

	public Boolean updateAssignment(StoryAssignment storyAssignment) {

		Boolean status = Boolean.FALSE;

		try {
			update(storyAssignment);
			status = Boolean.TRUE;
			LOGGER.info("story assignment saved");

		} catch (Exception e) {
			LOGGER.info("assignment save failed " + e);
		}

		return status;

	}

	@SuppressWarnings("unchecked")
	public List<StoryAssignment> getAssignmentByStudent1(Long studentId) {

		Query query = getDAOManager().createQuery(
				"select a from story_assignment a where  a.assignedTo=?1 and a.deleted=0");
		query.setParameter("1", studentId);

		List<StoryAssignment> assignments = (List<StoryAssignment>) query.getResultList();
		return assignments;
	}

	@SuppressWarnings("unchecked")
	public List<ReviewStoryVO> getAssignmentByStudent(Long studentId) {

		Query query = getDAOManager().createNativeQuery(SQLConstants.ASSIGNMETN_BY_USER);
		query.setParameter(1, studentId);
		ResultTransformer transformer = Transformers.aliasToBean(ReviewStoryVO.class);
		daoUtil.applyTransformer(query, transformer);

		List<ReviewStoryVO> stories = query.getResultList();
		return stories;
	}

	@SuppressWarnings("unchecked")
	public List<Story> getStoryByStudent(Long studentId) {

		Query query = getDAOManager().createQuery(
				"select s from story s, story_assignment a "
						+ "where s.id=a.storyId and a.assignedTo=?1 and s.deleted=0");
		query.setParameter("1", studentId);

		List<Story> story = query.getResultList();
		return story;
	}

	@SuppressWarnings("unchecked")
	public StoryAssignment getAssignment(Long studentId, Long storyId) {

		Query query = getDAOManager().createQuery(
				"select a from story_assignment a "
						+ "where a.storyId=?1 and a.assignedTo=?2 and a.deleted=0");
		query.setParameter("1", storyId);
		query.setParameter("2", studentId);

		StoryAssignment assignment = (StoryAssignment) query.getSingleResult();
		return assignment;
	}

	public List<StoryAssignment> getAssignmentsByStory(Long storyId) {
		Query query = getDAOManager().createQuery(
				"select a from story_assignment a where a.storyId=?1 and a.deleted=0");
		query.setParameter("1", storyId);

		List<StoryAssignment> assignments = query.getResultList();
		return assignments;
	}

}
