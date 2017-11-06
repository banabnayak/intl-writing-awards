package com.scholastic.intl.writingawards.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.Query;

import com.scholastic.intl.writingawards.entity.Student;
@Stateless
@Named
public class RegistrationDAOImpl extends GenericDAOSupport<Student, Long> {
	
	private static final long serialVersionUID = 1L;

	/***
	 * 
	 * @param id
	 * @return Student
	 */
	public Student getStudent(Long id){	
		Student student = getDAOManager().find(Student.class, id);
		return student;
	}
	
	/***
	 * 
	 * @param name
	 * @return
	 */	
	@SuppressWarnings("unchecked")
	public Student getStudentByEmail(String email) {
		Query query = getDAOManager().createNamedQuery("findStudentByEmail", Student.class);
		query.setParameter("name", email);
		List<Student> results = (ArrayList<Student>)query.getResultList();
		if(results != null && !results.isEmpty()){
			return results.get(0);
		}
		return null;
	}
}
