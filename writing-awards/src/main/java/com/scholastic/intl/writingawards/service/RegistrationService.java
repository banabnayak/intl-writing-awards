package com.scholastic.intl.writingawards.service;

import com.scholastic.intl.writingawards.entity.Student;
import com.scholastic.intl.writingawards.model.StudentRegistrationVO;

public interface RegistrationService {

	public String registerStudent(StudentRegistrationVO student);

	public Student getStudent(Long studentId);
}
