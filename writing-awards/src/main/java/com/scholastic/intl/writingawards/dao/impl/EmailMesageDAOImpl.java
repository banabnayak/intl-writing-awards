/**
 * 
 */
package com.scholastic.intl.writingawards.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.EmailMessage;

/**
 * @author madhusmita.nayak
 * 
 */
@Stateless
public class EmailMesageDAOImpl extends GenericDAOSupport<EmailMessage, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * LOGGER for the current class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailMesageDAOImpl.class);

	public EmailMesageDAOImpl() {
		super();
	}

	public EmailMesageDAOImpl(Class<EmailMessage> emailMessage) {
		super(emailMessage);
	}

	public Boolean persistEmailMessage(EmailMessage emailMessage) {
		boolean status = false;
		try {
			if (emailMessage != null) {
				save(emailMessage);
			}
			if (emailMessage != null && emailMessage.getId() != 0) {
				status = true;
			}

		} catch (Exception exception) {
			LOGGER.debug("Exception: ", exception);
		}
		return status;
	}

	public Boolean updateEmailMessage(EmailMessage emailMessage) {
		boolean status = false;
		try {
			if (emailMessage != null) {
				update(emailMessage);
			}
			if (emailMessage != null && emailMessage.getId() != 0) {
				status = true;
			}

		} catch (Exception exception) {
			LOGGER.debug("Exception: ", exception);
		}
		return status;
	}

	public EmailMessage getEmailPersisted(final int id) {
		final TypedQuery<EmailMessage> queryEmail = getDAOManager().createNamedQuery(
				EmailMessage.FIND_BY_ID, EmailMessage.class);
		queryEmail.setParameter("id", id);
		final List<EmailMessage> emailMessages = queryEmail.getResultList();
		if (emailMessages != null && !emailMessages.isEmpty()) {
			return emailMessages.get(0);
		} else {
			return null;
		}
	}

	public List<EmailMessage> retrieveUnsent() {
		final TypedQuery<EmailMessage> queryEmail = getDAOManager().createNamedQuery(
				EmailMessage.FIND_UNSENT_EMAIL, EmailMessage.class);
		final List<EmailMessage> emailMessages = queryEmail.getResultList();
		if (emailMessages != null && !emailMessages.isEmpty()) {
			return emailMessages;
		} else {
			return null;
		}
	}
}
