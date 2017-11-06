package com.scholastic.intl.writingawards.serviceImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.scholastic.intl.writingawards.dao.impl.SchoolDAOImpl;
import com.scholastic.intl.writingawards.entity.School;
import com.scholastic.intl.writingawards.model.SchoolVO;
import com.scholastic.intl.writingawards.service.SchoolService;
@Stateless
public class SchoolServiceImpl implements SchoolService {

	@Inject
	SchoolDAOImpl schoolDAOImpl;
	
	@Override 
	public SchoolVO getSchools(String schoolName) {
		List<School> schools = schoolDAOImpl.getSchools(schoolName);
		SchoolVO schoolVO = new SchoolVO();
		schoolVO.setSchool(schools);
		return schoolVO;
	}

	@Override
	public School getSchool(Long schoolId) {
		return schoolDAOImpl.getSchool(schoolId);
	}
	
	@Override
	public School saveSchool(School school) {
		
		School schoolExist = schoolDAOImpl.getSchoolByPrincipalEmail(school.getPrincipalEmail());
		if(schoolExist != null){
			return schoolExist;
		}else{
			schoolDAOImpl.save(school);
			return schoolDAOImpl.getSchoolByPrincipalEmail(school.getPrincipalEmail());
		}
	}

	
}
