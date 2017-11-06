/**
 * 
 */
package com.scholastic.intl.writingawards.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.scholastic.intl.writingawards.dao.impl.EmailMesageDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StudentDetailsDaoImpl;
import com.scholastic.intl.writingawards.dao.impl.TaskDaoImpl;
import com.scholastic.intl.writingawards.entity.AppConfig;
import com.scholastic.intl.writingawards.entity.EmailMessage;
import com.scholastic.intl.writingawards.entity.User;
import com.scholastic.intl.writingawards.job.EmailHelper;
import com.scholastic.intl.writingawards.job.SendEmailJob;
import com.scholastic.intl.writingawards.model.EmailMessageVO;

/**
 * @author madhusmita.nayak
 * 
 */
public class EmailServiceImpl {

	@Inject
	EmailHelper emailHelper;
	@Inject
	EmailMesageDAOImpl emailMesageDAOImpl;
	@Inject
	SendEmailJob sendMail;

	@Inject
	TaskDaoImpl taskDaoImpl;

	@Inject
	StudentDetailsDaoImpl studentDetailsDaoImpl;

	SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");

	public Boolean sendEmail(final EmailMessageVO emailMessageVO) {
		Boolean emailMessageStatus = false;
		EmailMessage emailMessageEntity = null;
		if (emailMessageVO != null) {
			emailMessageEntity = emailHelper.queueEmail(emailMessageVO);
			emailMessageStatus = emailMesageDAOImpl.persistEmailMessage(emailMessageEntity);
		}
		return emailMessageStatus;
	}

	public EmailMessageVO setEmailMessageVO(User user, String templateName) {
		EmailMessageVO emailMessageVO = new EmailMessageVO();

		emailMessageVO.setUserId(user.getId());
		emailMessageVO.setRecipientAddress(user.getUserId());
		Map<String, Object> globalParams = new HashMap<String, Object>();
		globalParams.put("fullName", user.getFullName());
		globalParams.put("email", user.getUserId());
		globalParams.put("password", user.getPassword());
		globalParams.put("regNo", user.getRegNo());
		if ("story_submission".equals(templateName)) {
			final Date reviewDate = taskDaoImpl.getTaskById(2L).getStartDate();
			if (reviewDate != null) {
				globalParams.put("rankingStartDate", ft.format(reviewDate));
			}
		}
		if ("result_announcment".equals(templateName)) {
			List<AppConfig> apppConfigs = studentDetailsDaoImpl.getAppConfig();
			if (apppConfigs != null && apppConfigs.get(4) != null) {
				globalParams.put("resultLink", apppConfigs.get(4).getConfigValue());
			}
		}
		emailMessageVO.setGlobalParams(globalParams);
		emailMessageVO.setTestEmail(Boolean.FALSE);
		emailMessageVO.setTemplateName(templateName);
		return emailMessageVO;

	}
}
