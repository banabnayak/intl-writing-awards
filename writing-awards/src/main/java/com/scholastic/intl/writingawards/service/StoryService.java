package com.scholastic.intl.writingawards.service;

import com.scholastic.intl.writingawards.entity.Story;
import com.scholastic.intl.writingawards.model.AuthUserVO;
import com.scholastic.intl.writingawards.model.StoryVO;

public interface StoryService {

	public Boolean saveStory(StoryVO storyVO, AuthUserVO authUserVO);
	public Story getStory(Long id);
	public Story getStoryByStudent(String studentId);

}
