/**
 * 
 */
package com.scholastic.intl.writingawards.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.Group;
import com.scholastic.intl.writingawards.entity.Topic;
import com.scholastic.intl.writingawards.job.SQLConstants;
import com.scholastic.intl.writingawards.model.UserTopicsVO;

/**
 * @author madhusmita.nayak
 * 
 */
@Named
@Stateless
public class TopicDAOImpl extends GenericDAOSupport<Topic, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	DAOUtilty daoUtil;

	/**
	 * LOGGER for the current class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TopicDAOImpl.class);

	public Topic addTopic(final Topic topic) {
		Topic newTopic = null;

		if (topic != null) {
			LOGGER.info("inside DAO .........");
			try {
				saveTopic(topic);
				if (topic.getId() != null) {
					newTopic = topic;
					LOGGER.info("topic added successfully");

				}
			} catch (Exception exception) {

				LOGGER.info("topic Falure " + exception);
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(exception.getMessage());
				}
			}
		}
		return newTopic;
	}

	private void saveTopic(Topic topic) {

		try {
			save(topic);

		} catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}
	}

	public boolean removeTopic(final Topic topic) {
		boolean status = false;
		try {

			Topic topicEntity = getDAOManager().find(Topic.class, topic.getId());
			if (topicEntity != null) {
				topicEntity.setDeleted(Boolean.TRUE);
				entityManager.merge(topicEntity);
				status = true;
				LOGGER.info("topic removed successfully");
			}

		} catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}

		return status;
	}

	public boolean validateStudentGroup(Long groupId, String groupName) {
		boolean status = false;
		try {
			Group studentGroup = getDAOManager().find(Group.class, groupId);
			if (studentGroup != null && groupName.equalsIgnoreCase(studentGroup.getName())) {
				status = true;
			}

		} catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}
		return status;
	}

	public Topic getTopicByName(final String name) {

		Topic topic = null;
		try {

			Query query = getDAOManager().createQuery("from topic where name=?1 and deleted=0");
			query.setParameter(1, name);

			topic = (Topic) query.getSingleResult();

		}

		catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}

		return topic;
	}

	public Topic getTopicByNameGroup(final String name, final Long groupId) {

		Topic topic = null;
		try {

			Query query = getDAOManager().createQuery(
					"from topic where name=?1 and group.id=?2 and deleted=0");
			query.setParameter(1, name);
			query.setParameter(2, groupId);

			topic = (Topic) query.getSingleResult();

		}

		catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}

		return topic;
	}

	public List<Topic> getTopics() {

		Query query = getDAOManager().createNamedQuery("getAllTopics", Topic.class);

		@SuppressWarnings("unchecked")
		List<Topic> result = ((ArrayList<Topic>) query.getResultList());
		return result;

	}

	public List<UserTopicsVO> getTopicsByEmail(String email) {

		Query query = getDAOManager().createNativeQuery(SQLConstants.TOPICS_BY_USER);
		query.setParameter(1, email);
		ResultTransformer transformer = Transformers.aliasToBean(UserTopicsVO.class);
		daoUtil.applyTransformer(query, transformer);
		List<UserTopicsVO> results = (ArrayList<UserTopicsVO>) query.getResultList();
		return results;

	}

	public Topic getTopic(final Long id) {
		return getDAOManager().find(Topic.class, id);
	}

	public Boolean deleteTopic(Long id) {

		Boolean status = Boolean.FALSE;
		Query query = getDAOManager().createQuery("update topic t set t.deleted=1 where t.id=?1");
		query.setParameter(1, id);

		int count = query.executeUpdate();
		if (count == 1) {
			status = Boolean.TRUE;
		}

		return status;
	}

}
