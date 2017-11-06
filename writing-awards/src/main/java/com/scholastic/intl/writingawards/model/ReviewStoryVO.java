package com.scholastic.intl.writingawards.model;

import java.math.BigInteger;

public class ReviewStoryVO {

	private BigInteger id;

	private BigInteger topic;

	private String title;

	private String storyText;
	
	private Integer totalMarks;
	
	public BigInteger getTopic() {
		return topic;
	}

	public void setTopic(BigInteger topic) {
		this.topic = topic;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStoryText() {
		return storyText;
	}

	public void setStoryText(String storyText) {
		this.storyText = storyText;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}
	
	@Override
	public String toString() {
		return "ReviewStoryVO [id=" + id + ", topic=" + topic + ", title="
				+ title + ", storyText=" + storyText + ", totalMarks="
				+ totalMarks + "]";
	}

}
