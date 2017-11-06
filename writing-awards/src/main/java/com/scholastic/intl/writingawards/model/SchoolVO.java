package com.scholastic.intl.writingawards.model;

import java.io.Serializable;
import java.util.List;

import com.scholastic.intl.writingawards.entity.School;


public class SchoolVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<School> school;

	public List<School> getSchool() {
		return school;
	}

	public void setSchool(List<School> school) {
		this.school = school;
	}
	
	

}
