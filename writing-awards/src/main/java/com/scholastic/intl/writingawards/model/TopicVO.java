/**
 * 
 */
package com.scholastic.intl.writingawards.model;

import java.io.Serializable;

/**
 * @author madhusmita.nayak
 *
 */
public class TopicVO extends CommonVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param topicId
	 */
	private Long topicId;
	/**
	 * @param groupId
	 */
	private Long groupId;
	/**
	 * @param groupName
	 */
	private String groupName;
	/**
	 * @param topicName
	 */
	private String topicName;
	/**
	 * @param topicDescription
	 */
	private String topicDescription;
	
	
	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(final Long groupId) {
		this.groupId = groupId;
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
	/**
	 * @return the topicDescription
	 */
	public String getTopicDescription() {
		return topicDescription;
	}
	/**
	 * @param topicDescription the topicDescription to set
	 */
	public void setTopicDescription(final String topicDescription) {
		this.topicDescription = topicDescription;
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
	 * @return the topicId
	 */
	public Long getTopicId() {
		return topicId;
	}
	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(final Long topicId) {
		this.topicId = topicId;
	}
	

}
