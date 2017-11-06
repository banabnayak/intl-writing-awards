package com.scholastic.intl.writingawards.model;

import java.io.Serializable;

import com.scholastic.intl.writingawards.entity.User;


public class AuthUserVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private Boolean isSaved;
	private Boolean isStorySubmitted;
	private Boolean isReviewStarted;
	private Boolean rememberMe;
	private Boolean loginStatus;
	private String regNo;
	private Boolean isReviewed;
	private String startDate;
	private String endDate;
	private Boolean isAnnouncementStarted;
	public Boolean isReviewClosed;

	
	public Boolean getIsSaved() {
		return isSaved;
	}

	public void setIsSaved(Boolean isSaved) {
		this.isSaved = isSaved;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsStorySubmitted() {
		return isStorySubmitted;
	}

	public void setIsStorySubmitted(Boolean isStorySubmitted) {
		this.isStorySubmitted = isStorySubmitted;
	}

	public Boolean getIsReviewStarted() {
		return isReviewStarted;
	}

	public void setIsReviewStarted(Boolean isReviewStarted) {
		this.isReviewStarted = isReviewStarted;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}






	public Boolean getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(Boolean loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Boolean getIsReviewed() {
		return isReviewed;
	}

	public void setIsReviewed(Boolean isReviewed) {
		this.isReviewed = isReviewed;
	}

	public Boolean getIsAnnouncementStarted() {
		return isAnnouncementStarted;
	}

	public void setIsAnnouncementStarted(Boolean isAnnouncementStarted) {
		this.isAnnouncementStarted = isAnnouncementStarted;
	}

	public Boolean getIsReviewClosed() {
		return isReviewClosed;
	}

	public void setIsReviewClosed(Boolean isReviewClosed) {
		this.isReviewClosed = isReviewClosed;
	}

	@Override
	public String toString() {
		return "AuthUserVO [user=" + user + ", isSaved=" + isSaved
				+ ", isStorySubmitted=" + isStorySubmitted
				+ ", isReviewStarted=" + isReviewStarted + ", rememberMe="
				+ rememberMe + ", loginStatus=" + loginStatus + ", regNo="
				+ regNo + ", isReviewed=" + isReviewed + ", startDate="
				+ startDate + ", endDate=" + endDate
				+ ", isAnnouncementStarted=" + isAnnouncementStarted
				+ ", isReviewClosed=" + isReviewClosed + "]";
	}
	
}
