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

public class QuartzJob implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuartzJob.class);

	@Inject SendEmailJob sendEmailCron;
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		sendEmailCron.execute();
		LOGGER.info("SWA CRON  + Quartz 2.2.1");
	}
}
