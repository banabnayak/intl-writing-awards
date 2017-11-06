package com.scholastic.intl.writingawards.api.v1.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.Task;
import com.scholastic.intl.writingawards.entity.Topic;
import com.scholastic.intl.writingawards.form.UploadForm;
import com.scholastic.intl.writingawards.helper.ServiceHelper;
import com.scholastic.intl.writingawards.helper.StatusCode;
import com.scholastic.intl.writingawards.model.AppConfigVO;
import com.scholastic.intl.writingawards.model.AuthUserVO;
import com.scholastic.intl.writingawards.model.TaskVO;
import com.scholastic.intl.writingawards.model.TopicVO;
import com.scholastic.intl.writingawards.service.AdminReportsService;
import com.scholastic.intl.writingawards.service.TaskService;
import com.scholastic.intl.writingawards.service.TopicService;

/**
 * @author madhusmita.nayak
 * 
 */
@Stateless
@Path("/admin")
public class AdminAPIService {
	/**
	 * LOGGER for the current class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminAPIService.class);

	private final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("dd.MM.yyyy");

	private final SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	private String FOLDERPATH = null;
	
	@Context
	private HttpServletRequest request;
	
	@Inject
	private AdminReportsService adminReportsApi;
	@Inject
	private ServiceHelper serviceHelper;
	@Inject
	private TaskService taskService;

	@Inject
	private TopicService topicService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("topStory")
	public Response topStoryLookup() {
		Response response = null;
		ResponseBuilder responsebuild = Response.ok();		
		List<AppConfigVO> appConfigVO = adminReportsApi.topStoryLookUp();
		responsebuild = Response.ok(appConfigVO);
		response = responsebuild.build();
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("topParticipant")
	public Response topParticipantLookup() {
		Response response = null;
		ResponseBuilder responsebuild = Response.ok();		
		List<AppConfigVO> appConfigVO = adminReportsApi.topParticipantsLookup();
		responsebuild = Response.ok(appConfigVO);
		response = responsebuild.build();
		return response;
	}
	
	@Path("topic/add")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTopic(TopicVO topicVO, @Context HttpServletRequest req) {
		if (topicVO != null) {

			final HttpSession session = req.getSession();
			final AuthUserVO authUserVO = (AuthUserVO) session.getAttribute("userObj");

			final Topic topic = topicService.addCompititionTopic(topicVO, authUserVO);
			if (topic != null) {
				topicVO.setTopicId(topic.getId());
				return Response.status(Response.Status.OK).entity(topicVO).build();
			}else {
				return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_ADDED)
						.build();
			}
		} else {
			return Response.status(Response.Status.OK).entity(StatusCode.BAD_REQUEST).build();
		}

	}

	@Path("topic/remove/{id}/{flag}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response removeTopic(@PathParam("id") Long topicId, @PathParam("flag") Boolean flag) {
		LOGGER.info(" Topic Id " + topicId + " isFirstAttempt=" + flag);
		if (topicId != null) {
			final Boolean status = topicService.removeCompititionTopic(topicId, flag);

			if (status) {
				return Response.status(Response.Status.OK).build();
			} else {
				LOGGER.info("topic cannot be removed");
				return Response.status(Response.Status.NO_CONTENT).build();
			}
		} else {
			return Response.status(Response.Status.OK).entity(StatusCode.BAD_REQUEST).build();
		}

	}

	@GET
	@Path("topic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopics() {
		final List<Topic> topics = topicService.getTopics();
		if (topics != null) {
			return Response.status(Response.Status.OK).entity(topics).build();
		} else {
			return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
		}
	}

	@SuppressWarnings("deprecation")
	@Path("task/add")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(TaskVO taskVO, @Context HttpServletRequest req) {
		if (taskVO != null) {

			final HttpSession session = req.getSession();
			final AuthUserVO authUserVO = (AuthUserVO) session.getAttribute("userObj");
			final Task task = new Task();

			task.setId(taskVO.getId());
			task.setDescription(taskVO.getDescription());

			try {
				LOGGER.info(" from UI " + taskVO.getStartDate());
				task.setStartDate(DATEFORMATTER.parse(taskVO.getStartDate()));
				task.setStartDate(new Date(task.getStartDate().getTime() + (24 * 60 * 60 * 1000)));
			} catch (ParseException e) {
				try {
					task.setStartDate(DATEFORMAT.parse(taskVO.getStartDate()));
				} catch (ParseException ex) {
					LOGGER.info("Error parsing date", ex);
					return Response.status(Response.Status.OK).entity(StatusCode.BAD_REQUEST).build();
				}
			}

			try {
				LOGGER.info(" bfr conversion " + taskVO.getEndDate());
				task.setEndDate(DATEFORMATTER.parse(taskVO.getEndDate()));
				task.setEndDate(new Date(task.getEndDate().getTime() + (24 * 60 * 60 * 1000)));
				task.setEndDate(DATEFORMAT.parse(DATEFORMAT.format(task.getEndDate())));
				LOGGER.info(" after conversion " + task.getEndDate());
				task.setEndDate(new Date(task.getEndDate().getTime()
						+ ((24 * 60 * 60 * 1000) - 10)));
				LOGGER.info(" after End date" + task.getEndDate());
			} catch (ParseException e) {
				try {
					task.setEndDate(DATEFORMAT.parse(taskVO.getEndDate()));
				} catch (ParseException ex) {
					LOGGER.info("Error parsing date", ex);
					return Response.status(Response.Status.OK).entity(StatusCode.BAD_REQUEST)
							.build();
				}
			}

			if (authUserVO != null) {
				if (task.getId() != null) {
					task.setUpdatedBy(authUserVO.getUser().getId());
				} else {
					task.setCreatedBy(authUserVO.getUser().getId());
					task.setUpdatedBy(authUserVO.getUser().getId());
				}
			}

			try {
				task.setStartDate(DATEFORMAT.parse(DATEFORMAT.format(task.getStartDate())));
			} catch (ParseException e) {
				LOGGER.info("ERROR Parsing start date");
			}

			Task task1 = taskService.addTask(task);

			if (task1 != null) {
				final TaskVO objTaskVO = new TaskVO();
				objTaskVO.setId(task1.getId());
				objTaskVO.setDescription(task1.getDescription());
				objTaskVO.setStartDate(DATEFORMAT.format(task1.getStartDate()));
				objTaskVO.setEndDate(DATEFORMAT.format(task1.getEndDate()));
				LOGGER.info("task VO Date " + objTaskVO.getStartDate() + " " + objTaskVO.getEndDate());
				return Response.status(Response.Status.OK).entity(objTaskVO).build();
			} else {
				return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_ADDED).build();
			}
		} else {
			return Response.status(Response.Status.OK).entity(StatusCode.BAD_REQUEST).build();
		}

	}

	@Path("task/remove/{id}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response removeTask(@PathParam("id") Long id) {
		if (id != null) {
			final Boolean status = taskService.removeTask(id);
			if (status) {
				return Response.status(Response.Status.OK).build();
			} else {
				return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_REMOVED).build();
			}
		} else {
			return Response.status(Response.Status.OK).entity(StatusCode.BAD_REQUEST).build();
		}

	}

	@GET
	@Path("task")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTasks() {
		LOGGER.info("Fetching Task ...................");
		final List<Task> tasklist = taskService.getAllTasks();
		if (tasklist != null) {
			LOGGER.info("Task found " + tasklist);
			final List<TaskVO> tasks = new ArrayList<TaskVO>();
			TaskVO taskVO = null;
			for (final Task task : tasklist) {

				taskVO = new TaskVO();
				taskVO.setId(task.getId());
				taskVO.setDescription(task.getDescription());
				if (task.getStartDate() != null) {
					taskVO.setStartDate(DATEFORMAT.format(task.getStartDate()));
				}
				if (task.getEndDate() != null) {
					taskVO.setEndDate(DATEFORMAT.format(task.getEndDate()));
				}
				tasks.add(taskVO);

			}
			return Response.status(Response.Status.OK).entity(tasks).build();
		} else {
			LOGGER.info("Task  not found ");
			return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
		}
	}

	/* File download and upload below */
	@GET
	@Path("/csvExport")
	@Produces(MediaType.WILDCARD)
	public Response exportCsv() {
		LOGGER.info("Students export");
		try {
			final StringBuilder sbStudentDetails = adminReportsApi.getStudentDetails();
			if (sbStudentDetails == null) {
				return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
			} else {
				ResponseBuilder response = Response.ok((Object) sbStudentDetails);
				return response.build();
			}
		} catch (Exception exception) {
			LOGGER.debug("Exception-exportCsv: ", exception);
			return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
		}

	}

	@GET
	@Path("/zipExport/{topStories}/{groupId}")
	@Produces(MediaType.WILDCARD)
	public Response exportZip(@PathParam("topStories") int topStories,
			@PathParam("groupId") int groupId) {

		LOGGER.info("Top stories #" + topStories + " for group " + groupId + " download ");
		try {
			final String tempDir = System.getProperty("java.io.tmpdir");
			final File tempFileZip = new File(tempDir, String.valueOf(System.currentTimeMillis() + 2000));
			final File tempFile = adminReportsApi.createPdf4Stories(topStories, groupId);
			if (tempFile == null) {
				return Response.status(Response.Status.NO_CONTENT)
						.entity(StatusCode.RESOURCE_NOT_FOUND).build();
			}
			LOGGER.info(tempFile.getPath());
			if (!tempFileZip.isDirectory()) {
				tempFileZip.mkdir();
			}
			final OutputStream outputStream = new FileOutputStream(tempFileZip + File.separator
					+ "topStoryZip.zip");
			final ZipOutputStream zos = new ZipOutputStream(outputStream);
			serviceHelper.zipDir(tempFile.getPath(), zos);
			FileUtils.deleteDirectory(tempFile);

			zos.close();
			outputStream.close();

			final File zipFile = new File(tempFileZip + File.separator + "topStoryZip.zip");
			final ResponseBuilder response = Response.ok((Object) zipFile);
			return response.build();
		} catch (Exception exception) {
			LOGGER.debug("Exception-exportZip: ", exception);
			return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
		}
	}

	@POST
	@Path("image/upload")
	@Consumes("multipart/form-data")
	@SuppressWarnings("deprecation")
	public Response uploadFile(@MultipartForm UploadForm form) {
		if (FOLDERPATH == null) {
			FOLDERPATH = request.getRealPath("/images");
		}

		final String fileName = FOLDERPATH + File.separator + form.getSaveAs();
		try {
			writeFile(form.getData(), fileName);
		} catch (Exception e) {
			LOGGER.debug("Exception-uploadFile: ", e);
		}
		LOGGER.info("Done");
		return Response.status(200)
				.entity("uploadFile is called, Uploaded file name : " + fileName).build();
	}

	private void writeFile(final byte[] content, final String filename) {

		FileOutputStream fop = null;
		try {
			final File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			fop = new FileOutputStream(file);
			fop.write(content);
			fop.flush();
		} catch (FileNotFoundException fnfe) {
			LOGGER.debug("FileNotFound: ", fnfe);
		} catch (IOException ioe) {
			LOGGER.debug("IOException: ", ioe);
		} finally {
			if (fop != null) {
				try {
					fop.close();
				} catch (IOException e) {
					LOGGER.debug("Finally Closing File: ", e);
				}
				fop = null;
			}
		}
	}
	/**
	 * Find All network schools
	 * @return
	 */
	@GET
	@Path("/network-school/{is-participated}")
	@Produces(MediaType.WILDCARD)
	public Response networkSchoolReport(@PathParam("is-participated") final boolean isParticipated) {
		LOGGER.info("network school export, participated {}", isParticipated);
		try {
			final StringBuilder sbNetworkSchools = adminReportsApi.getSchoolReport(isParticipated, Boolean.TRUE);
			if (sbNetworkSchools == null) {
				return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
			} else {
				ResponseBuilder response = Response.ok((Object) sbNetworkSchools);
				return response.build();
			}
		} catch (Exception exception) {
			LOGGER.debug("Exception-exportCsv for all network school export: ", exception);
			return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
		}

	}
	
	/**
	 * Find all submitted story student details
	 * 
	 * @return
	 */
	@GET
	@Path("/submission-report")
	@Produces(MediaType.WILDCARD)
	public Response getSubmittedReports() {
		try {
			final StringBuilder submittedDetails = adminReportsApi
					.getSubmittedReport();
			if (submittedDetails == null) {
				return Response.status(Response.Status.OK)
						.entity(StatusCode.RESOURCE_NOT_FOUND).build();
			} else {
				ResponseBuilder response = Response
						.ok((Object) submittedDetails);
				return response.build();
			}
		} catch (Exception exception) {
			LOGGER.debug(
					"Exception-exportCsv for all story submitted student export: ",
					exception);
			return Response.status(Response.Status.OK)
					.entity(StatusCode.RESOURCE_NOT_FOUND).build();
		}
	}

	/**
	 * Find All non network schools
	 * 
	 * @return
	 */
	@GET
	@Path("/non-network-school")
	@Produces(MediaType.WILDCARD)
	public Response networkSchoolReport() {
		try {
			final StringBuilder sbNonNetworkSchools = adminReportsApi
					.getSchoolReport(Boolean.TRUE, Boolean.FALSE);
			if (sbNonNetworkSchools == null) {
				return Response.status(Response.Status.OK)
						.entity(StatusCode.RESOURCE_NOT_FOUND).build();
			} else {
				ResponseBuilder response = Response
						.ok((Object) sbNonNetworkSchools);
				return response.build();
			}
		} catch (Exception exception) {
			LOGGER.debug("Exception-exportCsv for all network school export: ",
					exception);
			return Response.status(Response.Status.OK)
					.entity(StatusCode.RESOURCE_NOT_FOUND).build();
		}

	}
	
	/**
	 * Find top participants
	 * @return
	 */
	@GET
	@Path("/top-participants-report/{group-id}/{top-participants}")
	@Produces(MediaType.WILDCARD)
	public Response getTopParticipants(@PathParam("group-id") final int groupId,
			@PathParam("top-participants") final int topParticipants) {
		try {
			final StringBuilder topParticipantData = adminReportsApi.getTopParticipantsData(groupId,
					topParticipants);
			if (topParticipantData == null) {
				return Response.status(Response.Status.OK)
						.entity(StatusCode.RESOURCE_NOT_FOUND).build();
			} else {
				ResponseBuilder response = Response
						.ok((Object) topParticipantData);
				return response.build();
			}
		} catch (Exception exception) {
			LOGGER.debug("Exception-exportCsv for top participants export: ",
					exception);
			return Response.status(Response.Status.OK)
					.entity(StatusCode.RESOURCE_NOT_FOUND).build();
		}

	}
	/**
	 * Get all school data.
	 * @return
	 */
	@GET
	@Path("/all-school-data")
	@Produces(MediaType.WILDCARD)
	public Response exportAllSchoolData() {
		try {
			final StringBuilder sbStudentDetails = adminReportsApi.getAllSchoolDetails();
			if (sbStudentDetails == null) {
				return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
			} else {
				ResponseBuilder response = Response.ok((Object) sbStudentDetails);
				return response.build();
			}
		} catch (Exception exception) {
			LOGGER.debug("Exception-exportCsv for all school export: ", exception);
			return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
		}

	}
}
