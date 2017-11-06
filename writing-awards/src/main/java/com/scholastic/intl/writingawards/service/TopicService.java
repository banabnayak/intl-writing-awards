package com.scholastic.intl.writingawards.service;

import java.util.List;

import com.scholastic.intl.writingawards.entity.Topic;
import com.scholastic.intl.writingawards.model.AuthUserVO;
import com.scholastic.intl.writingawards.model.TopicVO;
import com.scholastic.intl.writingawards.model.UserTopicsVO;



public interface TopicService {

	public Topic addCompititionTopic(TopicVO topicVO, AuthUserVO authUserVO);
	public List<UserTopicsVO> getCompitionTopics(final String email);
	public List<Topic> getTopics();
	public boolean removeCompititionTopic(final Long id, final Boolean flag) ;

}
