package com.scholastic.intl.writingawards.model;

import java.util.ArrayList;

public class ReviewVO {

	Long storyId;
	ArrayList<QuestionVO> questions;

	public Long getStoryId() {
		return storyId;
	}

	public void setStoryId(Long storyId) {
		this.storyId = storyId;
	}

	public ArrayList<QuestionVO> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<QuestionVO> questions) {
		this.questions = questions;
	}

}
