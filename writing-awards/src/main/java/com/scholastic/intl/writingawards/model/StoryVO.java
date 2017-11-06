package com.scholastic.intl.writingawards.model;

import java.io.Serializable;

public class StoryVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long topicId;
	private String storyTitle;
	private String storyText;
	private String submit;
	
	
	public String getStoryTitle() {
		return storyTitle;
	}
	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}
	public String getStoryText() {
		return storyText;
	}
	public void setStoryText(String storyText) {
		this.storyText = storyText;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	
	
	
	
	
	

}
