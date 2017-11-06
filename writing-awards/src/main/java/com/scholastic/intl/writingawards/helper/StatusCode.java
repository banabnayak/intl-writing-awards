package com.scholastic.intl.writingawards.helper;

import java.io.Serializable;

public class StatusCode implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_NOT_ADDED = "Not Added";

	public static final String RESOURCE_NOT_REMOVED = "Not Removed";

	public static final String RESOURCE_NOT_UPDATED = "Not Updated";

	public static final String BAD_REQUEST = "Invalid Input Data";

	public static final String RESOURCE_NOT_FOUND = "Data Not Found";

	public static final String ERROR_IN_STUDENT_REGISTRATION = "Error in registration : "
			+ "Please check with phone number, email..etc..! formats";

}
