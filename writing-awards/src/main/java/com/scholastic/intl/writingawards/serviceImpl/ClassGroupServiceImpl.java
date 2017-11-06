package com.scholastic.intl.writingawards.serviceImpl;

import java.util.List;

import javax.inject.Inject;

import com.scholastic.intl.writingawards.dao.impl.ClassGroupDAOImpl;
import com.scholastic.intl.writingawards.entity.Group;
import com.scholastic.intl.writingawards.model.GroupVO;
import com.scholastic.intl.writingawards.service.ClassGroupService;

public class ClassGroupServiceImpl implements ClassGroupService {

	@Inject
	ClassGroupDAOImpl classGroupDAOImpl;

	@Override
	public GroupVO getGroups() {
		GroupVO groupVO = new GroupVO();
		List<Group> groups = classGroupDAOImpl.getGroups();
		groupVO.setGroup(groups);
		return groupVO;
	}
	
	
	
}
