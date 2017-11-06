package com.scholastic.intl.writingawards.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.Story;
import com.scholastic.intl.writingawards.job.SQLConstants;

@Named
@Default
@Stateless
public class StoryDAOImpl extends GenericDAOSupport<Story, Serializable> {

	@Inject
	TopicDAOImpl topicDAOImpl;

	private static final Logger LOGGER = LoggerFactory.getLogger(StoryDAOImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StoryDAOImpl() {
	}

	public StoryDAOImpl(Class<Story> story) {

		super(story);
	}

	public Story getStory(Long id) {

		return getDAOManager().find(Story.class, id);

	}

	public Boolean saveStory(Story story) {
		Boolean flag = Boolean.FALSE;
		try {
			save(story);

			if (story.getId() != null) {
				flag = Boolean.TRUE;
				LOGGER.info("Story Saved.");

			}
		} catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}

		return flag;
	}

	public Boolean updateStory(Story story) {
		Boolean flag = Boolean.FALSE;
		try {
			update(story);

			if (story.getId() != null) {
				flag = Boolean.TRUE;
				LOGGER.info("Story updated.");

			}
		} catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}

		return flag;
	}

	public Story getStoryByStudent(Long studentId) {
		Story story = null;

		try {
			Query query = getDAOManager().createQuery("select s from story s where s.studentId=?1");
			query.setParameter(1, studentId);
			story = (Story) query.getSingleResult();
			// System.out.println("Story "+story);

		}

		catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}

		return story;
	}

	public List<Story> getStoryByTopic(Long topicId) {
		List<Story> story = null;

		try {
			Query query = getDAOManager().createQuery("select s from story s where s.topic.id=?1");
			query.setParameter(1, topicId);
			story = query.getResultList();

		}

		catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}

		return story;
	}

	public void updateStoryTotalMark() {
		Query query = getDAOManager().createNativeQuery(SQLConstants.STORY_RATING_MARK_ZERO);

		List<BigInteger> resultList = query.getResultList();
		if (resultList != null && resultList.size() > 0) {
			for (BigInteger storyId : resultList) {
				Story storyEntity = getStory(storyId.longValue());
				storyEntity.setTotalMarks(0);
				updateStory(storyEntity);
			}
		}
	}
}