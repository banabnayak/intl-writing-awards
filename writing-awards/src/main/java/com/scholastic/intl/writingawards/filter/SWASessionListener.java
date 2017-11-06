package com.scholastic.intl.writingawards.filter;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.model.AuthUserVO;

public class SWASessionListener implements HttpSessionListener{
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SWASessionListener.class);

	public SWASessionListener(){
		LOGGER.info("SWASessionListener.SWASessionListener");
		
	}
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOGGER.info("Session created at " + new Date());
		HttpSession session = event.getSession();
		AuthUserVO authVo = (AuthUserVO)session.getAttribute("userObj");
		if(authVo != null){
			LOGGER.info("User:"+ authVo.getUser().getFullName() + " logged in");
			SWASecurityInterceptor.store(authVo.getUser());
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		LOGGER.info("Session destroyed at " + new Date());
		HttpSession session = event.getSession();
		AuthUserVO authVo = (AuthUserVO)session.getAttribute("userObj");
		if(authVo != null){
			LOGGER.info("User:"+ authVo.getUser().getFullName() + " logged out");
			SWASecurityInterceptor.remove(authVo.getUser());
		}
	}

}
