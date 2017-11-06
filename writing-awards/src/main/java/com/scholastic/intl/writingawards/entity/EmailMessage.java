package com.scholastic.intl.writingawards.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the email_message database table.
 * 
 */
@Entity
@Table(name = "email_message")
@NamedQueries({
		@NamedQuery(name = "EmailMessage.findAll", query = "SELECT e FROM EmailMessage e"),
		@NamedQuery(name = "EmailMessage.findById",
				query = "SELECT e FROM EmailMessage e where e.id=:id"),
		@NamedQuery(
				name = "EmailMessage.Unsent",
				query = "SELECT obj from EmailMessage obj where obj.sentDate is null and obj.ignoredDate is null") })
public class EmailMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * FIND_BY_EMAIL_MASSAGE_ID
	 */
	public static final String FIND_BY_ID = "EmailMessage.findById";
	public static final String FIND_UNSENT_EMAIL = "EmailMessage.Unsent";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "recipient_address")
	private String recipientAddress;

	private String subject;
	@Column(name = "template_type")
	private String templateType;

	@Column(name = "generated_date")
	private Date generatedDate;

	@Lob
	@Column(name = "html_body")
	private String htmlBody;

	@Column(name = "sent_date")
	private Date sentDate;
	@Column(name = "ignored_date")
	private Date ignoredDate;
	@Column(name = "user_id")
	private Long userId;

	public EmailMessage() {
	}

	public Date getGeneratedDate() {
		return this.generatedDate;
	}

	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	public String getHtmlBody() {
		return this.htmlBody;
	}

	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}

	public String getRecipientAddress() {
		return this.recipientAddress;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the sentDate
	 */
	public Date getSentDate() {
		return sentDate;
	}

	/**
	 * @param sentDate
	 *            the sentDate to set
	 */
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	/**
	 * @return the ignoredDate
	 */
	public Date getIgnoredDate() {
		return ignoredDate;
	}

	/**
	 * @param ignoredDate
	 *            the ignoredDate to set
	 */
	public void setIgnoredDate(Date ignoredDate) {
		this.ignoredDate = ignoredDate;
	}

}