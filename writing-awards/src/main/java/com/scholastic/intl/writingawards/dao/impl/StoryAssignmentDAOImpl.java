package com.scholastic.intl.writingawards.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;

import com.scholastic.intl.writingawards.entity.StoryAssignment;
import com.scholastic.intl.writingawards.job.SQLConstants;
import com.scholastic.intl.writingawards.model.StoryAssignmentDetailsVO;

@Stateless
public class StoryAssignmentDAOImpl extends GenericDAOSupport<StoryAssignment, Long> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	DAOUtilty daoUtil;

	@SuppressWarnings({ "unchecked" })
	public List<StoryAssignmentDetailsVO> getStoryDetails(){
		Query query = getDAOManager().createNativeQuery(SQLConstants.STORY_ASSIGNMENTS);
		ResultTransformer transformer = Transformers.aliasToBean(StoryAssignmentDetailsVO.class);
		daoUtil.applyTransformer(query,transformer);
		List<StoryAssignmentDetailsVO> results = query.getResultList();
		return results;
	}

}
