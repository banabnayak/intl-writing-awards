/**
 * 
 */
package com.scholastic.intl.writingawards.job;


import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.ejb.Asynchronous;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.dao.impl.EmailMesageDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StudentDetailsDaoImpl;
import com.scholastic.intl.writingawards.entity.AppConfig;
import com.scholastic.intl.writingawards.entity.EmailMessage;
import com.scholastic.intl.writingawards.helper.ServiceHelper;
public class SendEmailJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailJob.class);

	private static final String HTML_MIME_TYPE = "text/html";
	private static final String ALTERNATIVE_MULTIPART_SUBTYPE = "alternative";
	private static final String HOST_NAME="mail.smtp.host";
	private String fromAddress = null;

	private static final String HOST="smtp_host";

	private static final String FROM="from_address";

	@Inject EmailMesageDAOImpl emailMesageDAOImpl;
	@Inject ServiceHelper serviceHelper;
	@Inject StudentDetailsDaoImpl appConfigDetails;
	private boolean debug = true;
	@Asynchronous
	public void send(final EmailMessage pEmail) {
	    LOGGER.debug("Sending EmailMessage - Ref Id: " + pEmail.getId());
	  
	    try{
	    	Properties props = new Properties();
	    	List<AppConfig> configurationDetails = appConfigDetails.getAppConfig();
	    	for (AppConfig appConfig : configurationDetails) {
				if(HOST.equalsIgnoreCase(appConfig.getDescription())){
			    	props.put(HOST_NAME, StringUtils.stripToNull(appConfig.getConfigValue()));
				}else if(FROM.equalsIgnoreCase(appConfig.getDescription())){
					setFromAddress(appConfig.getConfigValue());
				}
			}
			Session session = Session.getDefaultInstance(props);
			session.setDebug(debug);
			MimeMessage message = new MimeMessage(session);
		
			    message.setFrom(new InternetAddress(fromAddress));
			    InternetAddress[] address = {new InternetAddress(pEmail.getRecipientAddress())};
			  
	            try {
	    		    message.setRecipients(Message.RecipientType.TO, address);
	            } catch (AddressException ae) {
	                LOGGER.error("Invalid Format for Recipient Address ["
	                        + pEmail.getRecipientAddress()
	                        + "]. Ignoring Message !");
	                LOGGER.debug("Exception: ", ae);
	                pEmail.setIgnoredDate(GregorianCalendar.getInstance().getTime());
	                emailMesageDAOImpl.updateEmailMessage(pEmail);
	                
	            }
	            if (pEmail.getIgnoredDate() == null) {

	    		    message.setSubject(pEmail.getSubject());

	                 //If CC needs to be sent to anyone, add the code here
	                 
	                 // multipart/alternative contains plaintext and html components 
	    		     // up to client application to decide which to display                
	                 MimeMultipart mimeMultipart = new MimeMultipart(ALTERNATIVE_MULTIPART_SUBTYPE);
	                 
	                 //create html part
	                 MimeBodyPart htmlPart = new MimeBodyPart();
	                 htmlPart.setContent(pEmail.getHtmlBody(), HTML_MIME_TYPE);
	                 mimeMultipart.addBodyPart(htmlPart);
	                 //message.setContent(mimeMultipart);
					 message.setContent(pEmail.getHtmlBody(), HTML_MIME_TYPE);

	     		    //message.setText(pEmail.getHtmlBody());

	                 LOGGER.debug("Sending Email to " + pEmail.getRecipientAddress());
	                 Transport.send(message);
	                 pEmail.setSentDate(GregorianCalendar.getInstance().getTime());
	                 LOGGER.debug("Saving EmailMessage - Ref Id: " + pEmail.getId() + " as Sent");
	                 emailMesageDAOImpl.updateEmailMessage(pEmail);
	            }

			          
	    } catch (MessagingException exception) {
	        LOGGER.debug("Sending EmailMessage Failed - Ref Id: " + pEmail.getId());
	        LOGGER.error("Failed Reason", exception);
	    }
	}

	
	public void execute() {
		LOGGER.debug("Email Job has been invoked to process all unsent emails.");
		List<EmailMessage> unsent = emailMesageDAOImpl.retrieveUnsent();
		if (unsent != null) {
			for (EmailMessage email : unsent) {
				send(email);
			}
		}
		LOGGER.debug("Email Job has processed all unsent emails.");
	}

	public void setFromAddress(String pFromAddress) {
		fromAddress = StringUtils.stripToNull(pFromAddress);
		if (fromAddress == null) {
			throw new IllegalArgumentException("fromAddress must be set");
		}
	}	
}
