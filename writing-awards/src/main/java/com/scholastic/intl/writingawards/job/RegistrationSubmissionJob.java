/**
 * 
 */
package com.scholastic.intl.writingawards.job;

import javax.inject.Inject;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author madhusmita.nayak
 *
 */
public class RegistrationSubmissionJob implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationSubmissionJob.class);
	@Inject SendEmailTaskProcessor taskEmailProcess;

	private final static String REGISTRATION_SUBMISSTION="Registration and Submission";

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		taskEmailProcess.executeRegdSubmissionJob(REGISTRATION_SUBMISSTION);
		LOGGER.info("SWA CRON regdSubmissionEmail Quartz 2.2.1");
	}

	

}
