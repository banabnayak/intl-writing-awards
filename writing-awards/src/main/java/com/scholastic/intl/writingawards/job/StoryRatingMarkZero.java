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
public class StoryRatingMarkZero implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(StoryRatingMarkZero.class);
	@Inject SendEmailTaskProcessor taskEmailProcess;

	private final static String STORY_RATING="Story Rating";

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		taskEmailProcess.executeStroyRatingMarkZeroJob(STORY_RATING);
		LOGGER.info("SWA CRON executeStroyRatingMarkZeroJob Quartz 2.2.1");
	}
}
