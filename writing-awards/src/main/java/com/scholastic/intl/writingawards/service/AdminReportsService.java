package com.scholastic.intl.writingawards.service;

import java.io.File;
import java.util.List;

import com.scholastic.intl.writingawards.model.AppConfigVO;

/**
 * @author madhusmita.nayak
 *
 */
public interface AdminReportsService {
	public List<AppConfigVO> topStoryLookUp();
	public StringBuilder getStudentDetails();

	public File createPdf4Stories(final int topStories, int groupId);

	public StringBuilder getSchoolReport(final boolean isParticipated,
			final boolean isNetworkSchool);
	public StringBuilder getTopParticipantsData(final int groupId, final int topParticipants);
	public List<AppConfigVO> topParticipantsLookup();
	public StringBuilder getSubmittedReport();
	public StringBuilder getAllSchoolDetails();
}
