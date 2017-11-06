package com.scholastic.intl.writingawards.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StoryAssignmentDetailsVO {

	private BigInteger studentId;

	private BigInteger storyId;

	private List<Long> assignedStories;

	private int assignmetCount;

	private BigInteger school;

	private String city;

	private String state;

	private short studentClass;

	private short studentGroup;

	public BigInteger getStudentId() {
		return studentId;
	}

	public void setStudentId(BigInteger studentId) {
		this.studentId = studentId;
	}

	public BigInteger getStoryId() {
		return storyId;
	}

	public void setStoryId(BigInteger storyId) {
		this.storyId = storyId;
	}

	public List<Long> getAssignedStories() {
		return assignedStories;
	}

	public void setAssignedStories(List<Long> assignedStories) {
		this.assignedStories = assignedStories;
	}

	public int getAssignmetCount() {
		return assignmetCount;
	}

	public void setAssignmetCount(int assignmetCount) {
		this.assignmetCount = assignmetCount;
	}

	public BigInteger getSchool() {
		return school;
	}

	public void setSchool(BigInteger school) {
		this.school = school;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public short getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(short studentClass) {
		this.studentClass = studentClass;
	}

	public short getStudentGroup() {
		return studentGroup;
	}

	public void setStudentGroup(short studentGroup) {
		this.studentGroup = studentGroup;
	}

	public StoryAssignmentDetailsVO() {
		super();
		assignedStories = new ArrayList<Long>();

	}

	@Override
	public String toString() {
		return "StoryAssignmentDetailsVO [studentId=" + studentId + ", storyId=" + storyId
				+ ", assignedStories=" + assignedStories + ", assignmetCount=" + assignmetCount
				+ ", school=" + school + ", city=" + city + ", State=" + state + "]";
	}

	public static final Comparator<StoryAssignmentDetailsVO> AssignedStoryComparator = 
			new Comparator<StoryAssignmentDetailsVO>() {
		@Override
		public int compare(StoryAssignmentDetailsVO o1, StoryAssignmentDetailsVO o2) {
			return o1.getAssignedStories().size() - o2.getAssignedStories().size();
		}

	};

}
