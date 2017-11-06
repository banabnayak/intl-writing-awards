package com.scholastic.intl.writingawards.api.v1.resources;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.dao.impl.UserDAOImpl;
import com.scholastic.intl.writingawards.entity.Question;
import com.scholastic.intl.writingawards.entity.Story;
import com.scholastic.intl.writingawards.helper.StatusCode;
import com.scholastic.intl.writingawards.model.AuthUserVO;
import com.scholastic.intl.writingawards.model.ReviewStoryVO;
import com.scholastic.intl.writingawards.model.ReviewVO;
import com.scholastic.intl.writingawards.model.StoryVO;
import com.scholastic.intl.writingawards.model.UserTopicsVO;
import com.scholastic.intl.writingawards.service.ReviewService;
import com.scholastic.intl.writingawards.service.StoryService;
import com.scholastic.intl.writingawards.service.TopicService;
import com.scholastic.intl.writingawards.service.UserService;

@Path("student")
public class StudentAPIService {

	@Context
	Request request;

	@Context
	HttpServletRequest req;

	@Inject
	StoryService storyService;

	@Inject
	ReviewService reviewService;

	@Inject
	TopicService topicService;

	// Need to be removed
	@Inject
	UserDAOImpl userDAOImpl;

	@Inject
	UserService service;

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentAPIService.class);

	@POST
	@Path("story/save")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response saveStory(StoryVO storyVO) {

		HttpSession session = req.getSession();
		AuthUserVO authUserVO = (AuthUserVO) session.getAttribute("userObj");
		// /Need to remove after session establishment

		if (authUserVO == null) {
			authUserVO = new AuthUserVO();
			authUserVO.setUser(userDAOImpl.getUser(1L));
		}

		LOGGER.info("User ID " + authUserVO.getUser().getId());
		Boolean status = storyService.saveStory(storyVO, authUserVO);

		if (status) {
			return Response.status(Response.Status.OK).entity(storyVO).build();
		} else {
			return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_ADDED)
					.build();
		}
	}

	@GET
	@Path("story/retrive")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserStory() {
		HttpSession session = req.getSession();
		AuthUserVO authUserVO = (AuthUserVO) session.getAttribute("userObj");
		if (authUserVO != null) {
			LOGGER.info("   student Id " + authUserVO.getUser().getId());
			Story story = (Story) storyService.getStoryByStudent(authUserVO.getUser().getUserId());

			if (story != null) {
				StoryVO storyVO = new StoryVO();
				storyVO.setTopicId(story.getTopic().getId());
				storyVO.setStoryTitle(story.getTitle());
				storyVO.setStoryText(story.getStoryText());
				storyVO.setSubmit(story.getSubmissionDt() != null ? "Submit" : "Save");
				LOGGER.info("Story Details " + storyVO.toString());
				return Response.status(Response.Status.OK).entity(storyVO).build();
			} else {
				return Response.status(Response.Status.OK).entity("Story Not Found").build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Story Not Found").build();
		}
	}

	@GET
	@Path("story/assignments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAssignedStories() {

		final HttpSession session = req.getSession();
		AuthUserVO authUserVO = (AuthUserVO) session.getAttribute("userObj");
		if (authUserVO == null) {
			authUserVO = new AuthUserVO();
			authUserVO.setUser(userDAOImpl.getUser(1L));
		}
		List<ReviewStoryVO> stories = reviewService.getAssignments(authUserVO.getUser().getUserId());

		if (stories == null) {
			return Response.status(Response.Status.OK).entity("Story Not Found").build();
		}
		for (ReviewStoryVO story : stories) {
			LOGGER.info(story.toString());
		}

		return Response.status(Response.Status.OK).entity(stories).build();
	}

	@GET
	@Path("story/activities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStoryActivities(@Context HttpServletRequest request) {
		LOGGER.info("story/activities.................................");
		HttpSession session = request.getSession();
		AuthUserVO authUserVO = (AuthUserVO) session.getAttribute("userObj");
		if (authUserVO != null) {
			service.storyActivities(authUserVO);
			session.setAttribute("userObj", authUserVO);
			LOGGER.info(authUserVO.toString());
			LOGGER.info("Story Dates " + authUserVO.getStartDate() + " " + authUserVO.getEndDate());
		}

		return Response.status(Response.Status.OK).entity(authUserVO).build();
	}

	@POST
	@Path("review/submit")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveMarks(ReviewVO reviewVO, @Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		AuthUserVO userVO = (AuthUserVO) session.getAttribute("userObj");

		if (userVO != null) {
			Boolean status = reviewService.saveReview(reviewVO, userVO.getUser().getUserId());
			if (status) {

				return Response.status(Response.Status.OK).build();
			} else {

				return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_ADDED)
						.build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(StatusCode.RESOURCE_NOT_ADDED).build();
		}

	}

	@GET
	@Path("review/retrive")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAssignments(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		AuthUserVO userVO = (AuthUserVO) session.getAttribute("userObj");

		if (userVO != null) {

			List<ReviewStoryVO> story = reviewService.getAssignments(userVO.getUser().getUserId());
			if (story != null) {
				return Response.status(Response.Status.OK).entity(story).build();
			} else {
				return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

	}

	@GET
	@Path("review/questions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuestions() {

		List<Question> questions = reviewService.getAllQuestions();
		LOGGER.info("questions " + questions.size());
		return Response.status(Response.Status.OK).entity(questions).build();
	}

	@GET
	@Path("topic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopics(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		AuthUserVO userVO = (AuthUserVO) session.getAttribute("userObj");
		if (userVO != null) {
			List<UserTopicsVO> topics = topicService.getCompitionTopics(userVO.getUser()
					.getUserId());

			if (topics != null) {
				return Response.status(Response.Status.OK).entity(topics).build();
			} else {
				return Response.status(Response.Status.OK).entity("Topic not found").build();
			}
		} else {
			return Response.status(Response.Status.OK).entity("User Not found").build();
		}

	}
}
