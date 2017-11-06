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

import com.scholastic.intl.writingawards.helper.StoryAssignmentHelper;


public class QuartzStoryAssignmentJob implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuartzStoryAssignmentJob.class);

	@Inject 
	private StoryAssignmentHelper assignmentHelper;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		assignmentHelper.execute();
		LOGGER.info("SWA CRON Quartz 2.2.1 for Story Assignment");
	}
}
