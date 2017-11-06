/**
 * 
 */
package com.scholastic.intl.writingawards.job;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.scholastic.intl.writingawards.entity.EmailMessage;
import com.scholastic.intl.writingawards.model.EmailMessageVO;

/**
 * @author nataraj.srikantaiah
 * 
 */
@Named
public class EmailHelper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String SUBJECT_TEMPLATE_SUFFIX = "_subject.vm";
	private static final String HTML_TEMPLATE_SUFFIX = "_html.vm";

	@Inject
	VelocityEngineUtils velocityEngineUtils;

	private String getTemplateName(final String templateName, final String templateSuffix) {
		StringBuilder strBuilder = new StringBuilder(templateName);
		strBuilder.append(templateSuffix);
		return strBuilder.toString();
	}

	private String mergeTemplate(final String templateName, final Map<String, Object> templateParams) {
		return velocityEngineUtils.mergeTemplateIntoString(templateName, templateParams);
	}

	public EmailMessage queueEmail(final EmailMessageVO emailMessages) {
		EmailMessage emailMessage = new EmailMessage();

		if (StringUtils.isBlank(emailMessages.getRecipientAddress())) {
			throw new IllegalArgumentException("recipientAddress cannot be blank for user "
					+ emailMessages.getUserId());
		}

		emailMessage.setUserId(emailMessages.getUserId());
		emailMessage.setRecipientAddress(emailMessages.getRecipientAddress());
		emailMessage.setTemplateType(emailMessages.getTemplateName());

		String subjectTemplate = getTemplateName(emailMessages.getTemplateName(),
				SUBJECT_TEMPLATE_SUFFIX);
		String subject = mergeTemplate(subjectTemplate, emailMessages.getGlobalParams());

		if (emailMessages.isTestEmail()) {
			subject = "TEST EMAIL ~ " + subject;
		}
		emailMessage.setSubject(subject);

		String htmlText = "";
		String htmlTemplate = getTemplateName(emailMessages.getTemplateName(), HTML_TEMPLATE_SUFFIX);
		htmlText = mergeTemplate(htmlTemplate, emailMessages.getGlobalParams());

		emailMessage.setHtmlBody(htmlText);
		emailMessage.setGeneratedDate(new Date());

		return emailMessage;

	}

}
