package com.scholastic.intl.writingawards.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.scholastic.intl.writingawards.entity.School;
import com.scholastic.intl.writingawards.entity.Student;

@XmlRootElement(name = "registration")
public class StudentRegistrationVO  implements Serializable{

	private static final long serialVersionUID = 1L;

	private Student student;
	private School school;
	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}
	/**
	 * @param student the student to set
	 */
	public void setStudent(final Student student) {
		this.student = student;
	}
	/**
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}
	/**
	 * @param school the school to set
	 */
	public void setSchool(final School school) {
		this.school = school;
	}
	
}
