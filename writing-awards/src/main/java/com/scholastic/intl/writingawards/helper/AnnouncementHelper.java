package com.scholastic.intl.writingawards.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.model.ConfigureTaskVO;
import com.scholastic.intl.writingawards.service.AnnouncementService;
import com.scholastic.intl.writingawards.service.ConfigureTaskService;

public class AnnouncementHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(StoryAssignmentHelper.class);

	@Inject
	private ConfigureTaskService configureTaskService;

	@Inject
	private AnnouncementService service;

	private final static String NEW_FORMAT = "yyyy-MM-dd";

	private static final Long TASK_ID = 3L;

	public void execute() {

		final ConfigureTaskVO configureTaskVO = configureTaskService.getSubmittedTask(TASK_ID);

		try {
			final SimpleDateFormat dateFormatter = new SimpleDateFormat(NEW_FORMAT);
			if (configureTaskVO != null
					&& configureTaskVO.getStartDate() != null
					&& Days.daysBetween(new LocalDate(),
							new LocalDate(dateFormatter.parse(configureTaskVO.getStartDate())))
							.getDays() == 0) {
				LOGGER.debug(" announcement JOB initiated..!");
				service.sendAnnouncements();
				LOGGER.debug(" announcement JOB completed..!");
			}

		} catch (ParseException e) {
			LOGGER.info("Opps...!! Error in Result Announcement job: Error date parsing..!");
			LOGGER.debug("ParseException: ", e);
		}

	}

}
