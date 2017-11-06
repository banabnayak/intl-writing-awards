package com.scholastic.intl.writingawards.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.dao.impl.ClassGroupDAOImpl;
import com.scholastic.intl.writingawards.dao.impl.StoryAssignmentDAOImpl;
import com.scholastic.intl.writingawards.entity.Group;
import com.scholastic.intl.writingawards.entity.StoryAssignment;
import com.scholastic.intl.writingawards.model.StoryAssignmentDetailsVO;
import com.scholastic.intl.writingawards.service.StoryAssignmentService;

public class StoryAssignmentServiceImpl implements StoryAssignmentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StoryAssignmentServiceImpl.class);

	@Inject
	StoryAssignmentDAOImpl assignmentDAOImpl;

	@Inject
	ClassGroupDAOImpl classGroupDAOImpl;

	List<Long> storyIds;
	List<StoryAssignmentDetailsVO> listOfStories;
	List<StoryAssignmentDetailsVO> assignmentRequiredStories;
	Map<Short, List<StoryAssignmentDetailsVO>> groupsOfStories;
	Map<Short, List<StoryAssignmentDetailsVO>> groupsForNextHierarchy;

	static final int STORY_ASSIGNMENT_COUNT = 3;
	static final int MIN_STORY_ASSIGNMENT_COUNT = 2;
	static final int MAX_STORY_ASSIGNMENT_COUNT = 4;

	static final int EXCEPTIONAL_STORY_ASSIGNMENT_COUNT = 5;

	static final String HIERARCHI_CITY = "CITY";
	static final String HIERARCHI_STATE = "STATE";
	static final String HIERARCHI_SCHOOL = "SCHOOL";

	static final String[] HIERARCHIES = { HIERARCHI_STATE, HIERARCHI_CITY, HIERARCHI_SCHOOL };

	@Override
	public void startStoryAssignment() {

		List<Group> totalGroups = classGroupDAOImpl.getGroups();
		List<StoryAssignmentDetailsVO> assignmentDetailsVOs = assignmentDAOImpl.getStoryDetails();

		if (totalGroups != null && totalGroups.size() > 0) {
			groupsOfStories = new HashMap<>();
			groupsForNextHierarchy = new HashMap<>();

			for (Group group : totalGroups) {
				groupsOfStories.put(Short.valueOf(group.getId().toString()),
						new ArrayList<StoryAssignmentDetailsVO>());
				LOGGER.info(group.getName() + " added to groupMap");
			}
			LOGGER.info("Group map creation completed..!!");

			if (assignmentDetailsVOs != null && assignmentDetailsVOs.size() > 0) {

				for (StoryAssignmentDetailsVO storyAssignmentDetailsVO : assignmentDetailsVOs) {
					groupsOfStories.get(storyAssignmentDetailsVO.getStudentGroup()).add(
							storyAssignmentDetailsVO);
				}
				LOGGER.info("Grouping of the stories completed..!!");

			} else {
				LOGGER.info("No Storie found..!!");
			}
			for (int j = 0; (j < HIERARCHIES.length); j++) {

				for (Map.Entry<Short, List<StoryAssignmentDetailsVO>> entry : groupsOfStories
						.entrySet()) {

					listOfStories = new ArrayList<>();
					short group = entry.getKey();

					listOfStories = entry.getValue();
					for (int i = MIN_STORY_ASSIGNMENT_COUNT - 1; i < MAX_STORY_ASSIGNMENT_COUNT + 1; i++) {

						if (isFurtherHirarchyProcessingRequire(entry.getValue())) {
							LOGGER.info("Group-" + group
									+ " stories assignment process started by " + HIERARCHIES[j]
									+ "..!!");

							listOfStories = this.assignByHirarchy(listOfStories, i, HIERARCHIES[j]);
							Collections.sort(listOfStories,
									StoryAssignmentDetailsVO.AssignedStoryComparator);

							LOGGER.info("Group-" + group
									+ " stories assignment process completed by " + HIERARCHIES[j]
									+ "..!!");
						}
					}
					groupsForNextHierarchy.put(group, listOfStories);
				}
				groupsOfStories = groupsForNextHierarchy;
			}

			persistAssignments();
			LOGGER.info("Story assignment successfully completed..!!");
		} else {
			LOGGER.info("No Group info found..!!");
		}

	}

	private void persistAssignments() {

		for (Map.Entry<Short, List<StoryAssignmentDetailsVO>> entry : groupsForNextHierarchy
				.entrySet()) {
			List<StoryAssignmentDetailsVO> assignmentDetailsVOsDup = new ArrayList<StoryAssignmentDetailsVO>();
			assignmentDetailsVOsDup = entry.getValue();
			if (isFurtherHirarchyProcessingRequire(assignmentDetailsVOsDup)) {
				LOGGER.info("Opps..System have no other choice @ this moment..!! ");
				getAssignmentRequiredStories(assignmentDetailsVOsDup);
				if (assignmentRequiredStories.size() > 0) {
					Collections.sort(assignmentDetailsVOsDup,
							StoryAssignmentDetailsVO.AssignedStoryComparator);
					listOfStories = this.assignByHirarchy(assignmentDetailsVOsDup,
							EXCEPTIONAL_STORY_ASSIGNMENT_COUNT, HIERARCHI_CITY);
					groupsForNextHierarchy.put(entry.getKey(), listOfStories);

				}
			}
		}
		for (Map.Entry<Short, List<StoryAssignmentDetailsVO>> entry : groupsForNextHierarchy
				.entrySet()) {
			for (StoryAssignmentDetailsVO storyAssignmentDetailsVO : entry.getValue()) {
				storyIds = storyAssignmentDetailsVO.getAssignedStories();
				for (Long assignedStoryId : storyIds) {
					StoryAssignment assignment = new StoryAssignment(assignedStoryId,
							Long.valueOf(storyAssignmentDetailsVO.getStudentId().toString()), 0);
					assignment.setDeleted(false);
					assignmentDAOImpl.save(assignment);
				}
			}
			lineSeperator();
			for (StoryAssignmentDetailsVO storyAssignmentDetailsVO : entry.getValue()) {
				LOGGER.info(storyAssignmentDetailsVO.toString());
			}
			lineSeperator();
		}
	}

	private List<StoryAssignmentDetailsVO> assignByHirarchy(
			List<StoryAssignmentDetailsVO> listOfStories, int assignmentCount, String hirarchiLevel) {
		boolean hirarchiCondition = true;
		if (listOfStories != null && listOfStories.size() > 0) {
			for (StoryAssignmentDetailsVO storyAssignmentDetailsVO : listOfStories) {

				for (StoryAssignmentDetailsVO storyAssignmentDetailsVODup : listOfStories) {

					if (hirarchiLevel.equals(HIERARCHI_STATE)) {
						hirarchiCondition = !storyAssignmentDetailsVODup.getState().equals(
								storyAssignmentDetailsVO.getState());
					} else if (hirarchiLevel.equals(HIERARCHI_CITY)) {
						hirarchiCondition = !storyAssignmentDetailsVODup.getCity().equals(
								storyAssignmentDetailsVO.getCity());
					} else if (hirarchiLevel.equals(HIERARCHI_SCHOOL)) {
						hirarchiCondition = !storyAssignmentDetailsVODup.getSchool().equals(
								storyAssignmentDetailsVO.getSchool());
					}

					if (storyAssignmentDetailsVO.getStudentId() != storyAssignmentDetailsVODup
							.getStudentId()
							&& storyAssignmentDetailsVODup.getAssignedStories().size() < assignmentCount
							&& !storyAssignmentDetailsVODup.getAssignedStories().contains(
									Long.valueOf(storyAssignmentDetailsVO.getStoryId().toString()))
							&& hirarchiCondition
							&& storyAssignmentDetailsVO.getAssignmetCount() < STORY_ASSIGNMENT_COUNT
							// Condition for Eliminate assignments to the
							// Students, who not participated in story writing
							&& (storyAssignmentDetailsVODup.getStoryId() != null && !storyAssignmentDetailsVODup
									.getStoryId().toString().trim().equals(""))) {

						storyAssignmentDetailsVODup.getAssignedStories().add(
								(Long.valueOf(storyAssignmentDetailsVO.getStoryId().toString())));
						storyAssignmentDetailsVO.setAssignmetCount(storyAssignmentDetailsVO
								.getAssignmetCount() + 1);
					}
				}
			}
		} else {
			LOGGER.info("Number of stories in group empty..!!");
		}
		return listOfStories;
	}

	private boolean isFurtherHirarchyProcessingRequire(List<StoryAssignmentDetailsVO> listOfStories) {

		for (StoryAssignmentDetailsVO storyAssignmentDetailsVO : listOfStories) {
			if (storyAssignmentDetailsVO.getAssignmetCount() < STORY_ASSIGNMENT_COUNT) {
				return true;
			}
		}
		return false;
	}

	private void getAssignmentRequiredStories(List<StoryAssignmentDetailsVO> listOfStories) {
		assignmentRequiredStories = new ArrayList<>();

		for (StoryAssignmentDetailsVO storyAssignmentDetailsVO : listOfStories) {
			if (storyAssignmentDetailsVO.getAssignmetCount() < STORY_ASSIGNMENT_COUNT) {
				assignmentRequiredStories.add(storyAssignmentDetailsVO);
			}
		}
	}

	private void lineSeperator() {
		StringBuffer line = new StringBuffer();
		for (int i = 0; i < 140; i++) {
			line.append("*");
		}
		LOGGER.info(line.toString());
	}

}
