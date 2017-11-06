package com.scholastic.intl.writingawards.service;

import com.scholastic.intl.writingawards.entity.School;
import com.scholastic.intl.writingawards.model.SchoolVO;

public interface SchoolService {
	
	public SchoolVO getSchools(String schoolName);
	public School getSchool(Long schoolId);
	public School saveSchool(School school);
}
