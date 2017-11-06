/**
 * 
 */
package com.scholastic.intl.writingawards.serviceImpl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.dao.impl.ClassGroupDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StoryDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.TopicDAOImpl;
import com.scholastic.intl.writingawards.entity.Group;
import com.scholastic.intl.writingawards.entity.Story;
import com.scholastic.intl.writingawards.entity.Topic;
import com.scholastic.intl.writingawards.helper.ServiceHelper;
import com.scholastic.intl.writingawards.model.AuthUserVO;
import com.scholastic.intl.writingawards.model.TopicVO;
import com.scholastic.intl.writingawards.model.UserTopicsVO;
import com.scholastic.intl.writingawards.service.TopicService;

/**
 * @author madhusmita.nayak
 * 
 */
public class TopicServiceImpl implements TopicService {
	/**
	 * LOGGER for the current class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TopicServiceImpl.class);

	/**
	 * Inject DAO LAYER
	 */
	@Inject
	TopicDAOImpl topicDaoImpl;

	@Inject
	ClassGroupDAOImpl classGroupDAOImpl;
	/**
	 * Inject Service Helper
	 */
	@Inject
	ServiceHelper serviceHelper;

	@Inject
	StoryDAOImpl storyDAOImpl;

	@Override
	public Topic addCompititionTopic(final TopicVO topicVO, AuthUserVO authUserVO) {
		// boolean validGroup = false;
		Topic topicAdded = null;
		if (topicVO != null) {

			Topic topic = topicDaoImpl.getTopicByNameGroup(topicVO.getTopicName(),
					topicVO.getGroupId());
			if (topic == null) {
				LOGGER.info(" safe to add ");
				Topic topicEntity = serviceHelper.setTopicEntity(topicVO);
				/**
				 * Add 0 to deleted field as we are creating a topic
				 */
				topicEntity.setDeleted(false);
				/**
				 * validate the student group
				 */
				// validGroup=topicDaoImpl.validateStudentGroup(topicVO.getGroupId(),
				// topicVO.getGroupName());

				// if(validGroup){

				LOGGER.info("Valid Group.............");
				// Group group=new Group();
				Group group = classGroupDAOImpl.getGroupByID(topicVO.getGroupId());
				LOGGER.info(group.toString());
				group.setId(topicVO.getGroupId());
				LOGGER.info(" Group ID +++++++++++++ " + group.getId());
				topicEntity.setGroup(group);

				if (authUserVO != null && authUserVO.getUser() != null) {

					topicEntity.setCreatedBy(authUserVO.getUser().getId());
					topicEntity.setUpdatedBy(authUserVO.getUser().getId());
				}
				topicAdded = topicDaoImpl.addTopic(topicEntity);

				LOGGER.info("Topic added status :" + topicAdded.getName());
				// }
				return topicAdded;
			} else {
				LOGGER.info(" duplicate topic name ");
				return topicAdded;
			}
		} else {
			return topicAdded;
		}
	}

	@Override
	public boolean removeCompititionTopic(final Long id, final Boolean flag) {
		boolean topicremoved = false;
		if (id != null) {
			// Topic topicEntity=serviceHelper.setUpdateTopic(topicVO);

			List<Story> stories = storyDAOImpl.getStoryByTopic(id);
			if (stories == null || stories.size() == 0) {
				if (!flag) {
					topicremoved = topicDaoImpl.deleteTopic(id);
					LOGGER.info("Topic removed status :" + topicremoved);
				} else {
					topicremoved = true;
				}
			} else {
				topicremoved = false;
			}

		}
		return topicremoved;
	}

	@Override
	public List<UserTopicsVO> getCompitionTopics(String email) {
		LOGGER.info("Topic Service IMp");
		List<UserTopicsVO> topics = topicDaoImpl.getTopicsByEmail(email);
		for (UserTopicsVO allTopicsVO : topics) {
			LOGGER.info(allTopicsVO.toString());
		}
		return topics;
	}

	@Override
	public List<Topic> getTopics() {
		return topicDaoImpl.getTopics();
	}

}
