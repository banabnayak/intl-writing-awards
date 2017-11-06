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
public class StoryRatingJob implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationSubmissionJob.class);
	@Inject SendEmailTaskProcessor taskEmailProcess;

	private final static String STORY_RATING="Story Rating";

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		taskEmailProcess.executeStroyRatingJob(STORY_RATING);
		LOGGER.info("SWA CRON executeStroyRatingJob Quartz 2.2.1");
	}

}