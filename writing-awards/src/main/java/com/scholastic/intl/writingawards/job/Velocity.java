package com.scholastic.intl.writingawards.job;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Velocity {

	private static final Logger LOGGER = LoggerFactory.getLogger(Velocity.class);

	private VelocityEngine velocityEngine;

	public Velocity() {
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

	public String execute() {
		try {
			// Build a context to hold the model
			VelocityContext velocityContext = new VelocityContext();
			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

			Map<String, String> map = new HashMap<String, String>();
			map.put("rno", "1");
			map.put("name", "Komal");
			map.put("cla", "Bca");
			list.add(map);

			map = new HashMap<String, String>();
			map.put("rno", "2");
			map.put("name", "Komal");
			map.put("cla", "Bca");
			list.add(map);
			velocityContext.put("stuDetails", list);

			// Execute the template
			StringWriter writer = new StringWriter();
			velocityEngine.mergeTemplate("template.vm", "utf-8", velocityContext, writer);

			// Return the result
			return writer.toString();
		} catch (Exception e) {
			LOGGER.debug("Exception: ", e);
		}
		return null;
	}

	public static void main(String[] args) {
		Velocity hello = new Velocity();
		LOGGER.info("Template returned: " + hello.execute());
	}
}