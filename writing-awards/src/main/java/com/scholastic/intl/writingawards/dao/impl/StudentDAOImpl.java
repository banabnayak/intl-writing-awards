package com.scholastic.intl.writingawards.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.Student;
@Stateless

public class StudentDAOImpl extends GenericDAOSupport<Student, Serializable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StudentDAOImpl.class);

	public Student getStudentByReg(String regNo) {

		Query query = getDAOManager().createQuery(
				"from student s where s.regNo=?1 and s.deleted=0");
		query.setParameter(1, regNo);

		try {
			Student student = (Student) query.getSingleResult();
			return student;
		} catch (NoResultException e) {

			LOGGER.info("Student Not Found" + e);
			return null;
		}
	}
	
	public Student getStudentByEmail(String email) {

		Query query = getDAOManager().createQuery(
				"from student s where parentEmail=?1 and s.deleted=0 ");
		query.setParameter(1, email);

		try {
			Student student = (Student) query.getSingleResult();
			return student;
		} catch (NoResultException e) {

			LOGGER.info("Student Not Found" + e);
			return null;
		}
	}
	public List<Student> getAllStudents() {

		Query query = getDAOManager().createQuery(
				"from student s where s.deleted=0 ",Student.class);
		try {
			List<Student> studentList =query.getResultList();
			return studentList;
		} catch (NoResultException e) {

			LOGGER.info("Student Not Found" + e);
			return null;
		}
	}
}
