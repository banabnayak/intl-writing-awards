package com.scholastic.intl.writingawards.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "story_assignment")
public class StoryAssignment extends CommonEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StoryAssignment(){}
	public StoryAssignment(long storyId, Long assignedTo, Integer totalMarks) {
		  super();
		  this.storyId = storyId;
		  this.assignedTo = assignedTo;
		  this.totalMarks = totalMarks;
		 }
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "story_id")
	private long storyId;

	
	@Column(name="assigned_to")
	private Long assignedTo;

	// public Long assigneeId;

	@Column(name = "total_marks")
	private Integer totalMarks;

//	@Column(name = "assigned_to")
//	public Long assigneeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getStoryId() {
		return storyId;
	}

	public void setStoryId(long storyId) {
		this.storyId = storyId;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	public Long getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}


	
	
	

}
