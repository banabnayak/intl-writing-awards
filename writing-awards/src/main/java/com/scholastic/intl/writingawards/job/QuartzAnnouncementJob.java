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

import com.scholastic.intl.writingawards.helper.AnnouncementHelper;


public class QuartzAnnouncementJob implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuartzAnnouncementJob.class);

	@Inject 
	private AnnouncementHelper announcementHelper;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		announcementHelper.execute();
		LOGGER.info("SWA CRON Quartz 2.2.1 for Story announcement");
	}
}
