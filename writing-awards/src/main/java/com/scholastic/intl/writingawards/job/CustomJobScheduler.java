/**
 * 
 */
package com.scholastic.intl.writingawards.job;

/**
 * @author madhusmita.nayak
 *
 */
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.solder.logging.Logger;
import org.jboss.solder.servlet.WebApplication;
import org.jboss.solder.servlet.event.Destroyed;
import org.jboss.solder.servlet.event.Started;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

@Named
@ApplicationScoped
public class CustomJobScheduler {

	private Scheduler scheduler;
	@Inject
	private CdiJobFactory cdiJobFactory;
	@Inject
	private Logger logger;

	public void onStartup(@Observes @Started WebApplication webapp) {

		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.setJobFactory(cdiJobFactory);

			// Setup the Job class and the Job group
			final JobDetail job = newJob(QuartzJob.class).withIdentity("CronQuartzJob", "Group")
					.build();
			/**
			 * Setting registration and submission email sending job
			 */
			final JobDetail regdSubmissionJob = newJob(RegistrationSubmissionJob.class)
					.withIdentity("CronRegdSubmissionJob", "GroupRegd").build();

			/**
			 * Setting story rating email sending job
			 */
			final JobDetail storyRatingJob = newJob(StoryRatingJob.class).withIdentity(
					"storyRatingJob", "GroupStory").build();

			// Story assignment JOB
			final JobDetail storyAssignmentJob = newJob(QuartzStoryAssignmentJob.class)
					.withIdentity("storyAssignmentJob", "GroupStoryAssignment").build();

			/**
			 * Setting story rating total marks as zero if any one assigned
			 * student story mark is zero
			 */
			final JobDetail storyRatingMarkZeroJob = newJob(StoryRatingMarkZero.class)
					.withIdentity("storyRatingMarkJob", "MarkZeorStoryRating").build();

			// Create a Trigger that fires every 5 minutes.

			/**
			 * As per the client request disabling the story announcement quartz job
			 * final JobDetail storyAnnouncementJob = newJob(QuartzAnnouncementJob.class)
			 *.withIdentity("storyAnnouncementJob", "GroupAnnouncement").build();
			 */

			final Trigger trigger = newTrigger().withIdentity("TriggerName", "Group")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ?")).build();
			// This trigger that fires every day at 7 PM .

			final Trigger regdSubmissiontrigger = newTrigger()
					.withIdentity("CronRegdSubmissionTrigger", "GroupRegd")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 00 19 * * ?")).build();
			// This trigger that fires every day at 1 AM .

			final Trigger storyRatingTrigger = newTrigger()
					.withIdentity("CronStoryRatingTrigger", "GroupStory")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 00 1 * * ?")).build();

			// Trigger for Story assignmetn JOB
			final Trigger storyAssignmentTrigger = newTrigger()
					.withIdentity("storyAssignmentTrigger", "GroupStoryAssignment")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 * * ?")).build();

			// This trigger that fires every day at 1 AM to mark story total
			// mark as zero.
			final Trigger storyRatingMarkZeroTrigger = newTrigger()
					.withIdentity("CronStoryRatingMarkZeroTrigger", "MarkZeorStoryRating")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 00 1 * * ?")).build();

			/**
			 * As per the client request disabling the story announcement quartz job
			 * final Trigger storyAnnouncementTrigger = newTrigger()
			.withIdentity("storyAnnouncementTrigger", "GroupAnnouncement")
			.withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ?")).build();
			 */
			// Setup the Job and Trigger with Scheduler & schedule jobs
			scheduler = new StdSchedulerFactory().getScheduler();

			scheduler.scheduleJob(job, trigger);
			scheduler.scheduleJob(regdSubmissionJob, regdSubmissiontrigger);
			scheduler.scheduleJob(storyRatingJob, storyRatingTrigger);
			scheduler.scheduleJob(storyAssignmentJob, storyAssignmentTrigger);
			scheduler.scheduleJob(storyRatingMarkZeroJob, storyRatingMarkZeroTrigger);
			// scheduler.scheduleJob(storyRatingMarkZeroJob,
			// storyRatingMarkZeroTrigger);
			/**
			 * As per the client request disabling the story announcement quartz job
			 * scheduler.scheduleJob(storyAnnouncementJob, storyAnnouncementTrigger);
			 */
			// Start the scheduler a minute after the webapplication is started.
			scheduler.start();

		} catch (SchedulerException e) {
			logger.error("Unable to start Scheduler ", e);
		}

	}

	public void onShutdown(@Observes @Destroyed WebApplication webapp) {
		if (scheduler != null) {
			try {
				scheduler.shutdown(true);
			} catch (SchedulerException e) {
				logger.error("Error while shutting down scheduler", e);
			}
		}

	}
}
