package com.scholastic.intl.writingawards.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "storyReview")
public class StoryReview extends CommonEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "story_assignment_id")
	private Long storyAssignmentId;

	@Column
	private Integer weightage;

	@Column(name = "question_id")
	private Long questionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStoryAssignmentId() {
		return storyAssignmentId;
	}

	public void setStoryAssignmentId(Long storyAssignmentId) {
		this.storyAssignmentId = storyAssignmentId;
	}

	public Integer getWeightage() {
		return weightage;
	}

	public void setWeightage(Integer weightage) {
		this.weightage = weightage;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}