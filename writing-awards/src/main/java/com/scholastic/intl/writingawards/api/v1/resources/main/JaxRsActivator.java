package com.scholastic.intl.writingawards.api.v1.resources.main;

import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author shailu.chougale
 *
 */
public class JaxRsActivator extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(JaxRsActivator.class);
	
	/**
	 * 
	 */
	public JaxRsActivator() {
		super();
		LOGGER.info("JaxRsActivator...");
	}
	
	
	
	
}