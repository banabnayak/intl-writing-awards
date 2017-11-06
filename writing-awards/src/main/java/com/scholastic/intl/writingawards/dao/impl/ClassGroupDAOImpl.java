package com.scholastic.intl.writingawards.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import com.scholastic.intl.writingawards.entity.Group;

@Named
public class ClassGroupDAOImpl extends GenericDAOSupport<Group, Long> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<Group> getGroups() {

		Query query = getDAOManager().createNamedQuery("findAllGroups", Group.class);
		List<Group> results = (ArrayList<Group>)query.getResultList();
		
		return results;
	}
	
	public Group getGroupByID(Long id){
		
		return getDAOManager().find(Group.class, id);
	}

}
