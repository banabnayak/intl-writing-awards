package com.scholastic.intl.writingawards.filter;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.User;

@Provider
@ServerInterceptor
public class SWASecurityInterceptor implements PreProcessInterceptor {
	private static final String PUBLIC_RESOURCE = "SWA PUBLIC:ALL";
	private static final String RRIVATE_RESOURCE = "SWA PRIVATE:NONE";
	private static final String AUTHENTICATION_SCHEME = "SWA";
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String ACCESS_DENIED = "Access denied for this resource";
	private static final String UNAUTHORIZED_ACCESS = "Unauthorized Access";
	private static final String INVALID_REQUEST = "Invalid Request";

	private static final Logger LOGGER = LoggerFactory.getLogger(SWASecurityInterceptor.class);
	private static final Set<String> ALL_AUTH_USERS = new CopyOnWriteArraySet<>();

	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod method) throws Failure,
			WebApplicationException {
		ServerResponse response = null;
		LOGGER.info("SecurityInterceptor:$:PATH=>" + request.getPreprocessedPath());
		HttpHeaders headers = request.getHttpHeaders();
		List<String> authList = headers.getRequestHeader(AUTHORIZATION_PROPERTY);

		if (authList == null || authList.isEmpty()) {
			LOGGER.info("SecurityInterceptor:405");
			response = new ServerResponse(INVALID_REQUEST, 405, new Headers<Object>());
		} else {
			LOGGER.info("SecurityInterceptor:AUTHKEY=>" + authList.get(0));
			final String AUTHKEY = authList.get(0);
			if (RRIVATE_RESOURCE.equals(AUTHKEY)) {
				LOGGER.info("Not Authenticated ............ ");
				response = new ServerResponse(ACCESS_DENIED, 401, new Headers<Object>());
			} else if (!PUBLIC_RESOURCE.equals(AUTHKEY)) {
				final String AUTHTOKEN = AUTHKEY.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
				LOGGER.info("SecurityInterceptor:AUTHTOKEN=>" + AUTHTOKEN);
				if (!ALL_AUTH_USERS.contains(AUTHTOKEN)) {
					response = new ServerResponse(UNAUTHORIZED_ACCESS, 402, new Headers<Object>());
				}
			}
		}
		return response;
	}

	public static void store(final User user) {
		String authToken = user.getUserId() + ":" + user.getRole();
		ALL_AUTH_USERS.add(authToken);
	}

	public static void remove(final User user) {
		String authToken = user.getUserId() + ":" + user.getRole();
		ALL_AUTH_USERS.remove(authToken);
	}

	public static Integer size() {
		return ALL_AUTH_USERS.size();
	}
}