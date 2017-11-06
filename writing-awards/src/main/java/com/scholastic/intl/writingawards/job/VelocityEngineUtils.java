/**
 * 
 */
package com.scholastic.intl.writingawards.job;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import javax.inject.Named;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author madhusmita.nayak
 * 
 */
@Named
public class VelocityEngineUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(VelocityEngineUtils.class);
	private VelocityEngine velocityEngine;

	public VelocityEngineUtils() {
		try {
			// Load the velocity properties from the class path
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("velocity.properties"));

			// Create and initialize the template engine
			velocityEngine = new VelocityEngine(properties);
		} catch (Exception e) {
			LOGGER.debug("Exception: ", e);
		}
	}

	public String mergeTemplateIntoString(final String templateName,
			final Map<String, Object> templateParams) {
		VelocityContext velocityContext = new VelocityContext();
		ArrayList<Map<String, Object>> templateList = new ArrayList<Map<String, Object>>();
		templateList.add(templateParams);
		velocityContext.put("swaTemplate", templateList);
		// Execute the template
		StringWriter writer = new StringWriter();
		velocityEngine.mergeTemplate(templateName, "utf-8", velocityContext, writer);

		// Return the result
		return writer.toString();
	}

}
