/**
 * 
 */
package com.scholastic.intl.writingawards.model;

import java.util.Map;


/**
 * @author madhusmita.nayak
 *
 */
public class EmailMessageVO {
	private Long userId;
	private String recipientAddress; 
	private String templateName;
	private Map<String, Object> globalParams;
	private boolean testEmail;
	
	public String getRecipientAddress() {
		return recipientAddress;
	}
	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Map<String, Object> getGlobalParams() {
		return globalParams;
	}
	public void setGlobalParams(Map<String, Object> globalParams) {
		this.globalParams = globalParams;
	}
	public boolean isTestEmail() {
		return testEmail;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setTestEmail(boolean testEmail) {
		this.testEmail = testEmail;
	}
}
