package com.scholastic.intl.writingawards.api.v1.resources;

import java.util.List;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.State;
import com.scholastic.intl.writingawards.entity.User;
import com.scholastic.intl.writingawards.filter.SWASecurityInterceptor;
import com.scholastic.intl.writingawards.helper.ServiceHelper;
import com.scholastic.intl.writingawards.helper.StatusCode;
import com.scholastic.intl.writingawards.model.AuthUserVO;
import com.scholastic.intl.writingawards.model.GroupVO;
import com.scholastic.intl.writingawards.model.SchoolVO;
import com.scholastic.intl.writingawards.model.StateListVO;
import com.scholastic.intl.writingawards.model.StudentRegistrationVO;
import com.scholastic.intl.writingawards.model.UserDetailsVO;
import com.scholastic.intl.writingawards.service.ClassGroupService;
import com.scholastic.intl.writingawards.service.RegistrationService;
import com.scholastic.intl.writingawards.service.SchoolService;
import com.scholastic.intl.writingawards.service.StateService;
import com.scholastic.intl.writingawards.service.StoryAssignmentService;
import com.scholastic.intl.writingawards.service.UserService;

@Path("public")
public class PublicAPIService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PublicAPIService.class);

	@Inject
	private UserService service;

	@Inject
	private RegistrationService registrationService;

	@Inject
	private StoryAssignmentService assignmentService;

	@Inject
	private SchoolService schoolService;

	@Inject
	private ClassGroupService classGroupService;
	
	@Inject
	private ServiceHelper serviceHelper;
	
	@Inject
	private StateService stateService;

	@Path("auth/launch")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkRegistartionDate() {
		LOGGER.info("validating registation date  ..........");
		final Boolean isRegOver = service.checkRegistartionDate();
		return Response.status(Response.Status.OK).entity(isRegOver).build();
	}

	/**
	 * 
	 * @param credentials
	 * @return
	 */

	@Path("auth/login")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateUser(final AuthUserVO pAuthUserVO, @Context final HttpServletRequest request) {
		LOGGER.info("Authenticatin ..........");
		final AuthUserVO authUserVO = service.authenticate(pAuthUserVO);
		if (authUserVO.getUser() == null) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} else {
			LOGGER.info("Successfully logged in ...");
			HttpSession session = request.getSession(true);
			session.setAttribute("userObj", authUserVO);
			SWASecurityInterceptor.store(authUserVO.getUser());
			return Response.status(Response.Status.OK).entity(authUserVO).build();
		}
	}

	/**
	 * 
	 * @param userDetails
	 * @return
	 */
	@Path("auth/resetpassword")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response resetPassword(final UserDetailsVO userDetails) {
		final User user = service.resetPassword(userDetails.getRegNo(), userDetails.getEmail());
		if (user == null) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} else {
			return Response.status(Response.Status.OK).build();
		}
	}

	@Path("auth/logout")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response logout(@Context final HttpServletRequest request) {
		final HttpSession session = request.getSession(true);
		final AuthUserVO authVo = (AuthUserVO) session.getAttribute("userObj");
		SWASecurityInterceptor.remove(authVo.getUser());
		session.invalidate();
		LOGGER.info("Successfully logged out ...");
		return Response.status(Response.Status.OK).entity("Logout").build();
	}

	@POST
	@Path("registration/save")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response registerStudent(final StudentRegistrationVO student) {
		try {
			final String registeredError = registrationService.registerStudent(student);
			if (registeredError != null) {
				return Response.status(Response.Status.OK).entity(registeredError).build();
			}
			return Response.status(Response.Status.OK).entity("SUCCESS").build();
		} catch (Exception e) {
			LOGGER.debug("Erro in registering the student having email: ", e);
			return Response.status(Response.Status.OK)
					.entity(StatusCode.ERROR_IN_STUDENT_REGISTRATION).build();
		}

	}

	// API only for testing purpose, will be removed after UAT.
	@GET
	@Path("registration/assign")
	public void test() {
		assignmentService.startStoryAssignment();
	}

	@GET
	@Path("/schools/{schoolName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public SchoolVO getListOfSchools(@PathParam("schoolName") String schoolName) {
		return schoolService.getSchools(schoolName);
	}

	@Path("/classGroups")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public GroupVO getListOfSchools() {
		return classGroupService.getGroups();
	}
	
	/**
	 * Find All states and cities
	 * @return
	 */
	@GET
	@Path("/state-city-list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStatesAndCities() {
		final List<State> stateList = stateService.getAllStates();
		if (stateList != null && !stateList.isEmpty()) {
			final StateListVO stateListVO = serviceHelper.getStateCityVOList(stateList);
			return Response.status(Response.Status.OK).entity(stateListVO).build();
		} else {
			return Response.status(Response.Status.OK).entity(StatusCode.RESOURCE_NOT_FOUND).build();
		}
	}
}
