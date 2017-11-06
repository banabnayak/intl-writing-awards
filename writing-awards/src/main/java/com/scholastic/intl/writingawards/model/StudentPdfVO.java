/**
 * 
 */
package com.scholastic.intl.writingawards.model;

import java.io.Serializable;

/**
 * @author madhusmita.nayak
 *
 */
public class StudentPdfVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String studentName;
	private String registrationNumber;
	private String schoolName;
	private String groupName;
	private String storyTitle;
	private String storyText;
	private String topicName;
	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(final String studentName) {
		this.studentName = studentName;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(final String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}
	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(final String schoolName) {
		this.schoolName = schoolName;
	}
	/**
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	/**
	 * @param registrationNumber the registrationNumber to set
	 */
	public void setRegistrationNumber(final String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	/**
	 * @return the storyTitle
	 */
	public String getStoryTitle() {
		return storyTitle;
	}
	/**
	 * @param storyTitle the storyTitle to set
	 */
	public void setStoryTitle(final String storyTitle) {
		this.storyTitle = storyTitle;
	}
	/**
	 * @return the storyText
	 */
	public String getStoryText() {
		return storyText;
	}
	/**
	 * @param storyText the storyText to set
	 */
	public void setStoryText(final String storyText) {
		this.storyText = storyText;
	}
	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}
	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(final String topicName) {
		this.topicName = topicName;
	}
}
