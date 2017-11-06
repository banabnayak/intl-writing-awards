package com.scholastic.intl.writingawards.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.Question;
import com.scholastic.intl.writingawards.entity.StoryReview;

@Stateless
public class ReviewDAOImpl extends GenericDAOSupport<StoryReview, Serializable> {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDAOImpl.class);

	/***
	 * 
	 * @param storyReview
	 * @return
	 */
	public Boolean saveReview(StoryReview storyReview) {
		Boolean status = Boolean.FALSE;
		if (storyReview != null) {
			try {
				save(storyReview);
				if (storyReview.getId() != null) {
					status = true;
					LOGGER.info("Story review saved successfully");

				}
			} catch (Exception exception) {
				LOGGER.debug("Exception: ", exception);
			}
		}

		return status;
	}

	public Boolean saveReviews(List<StoryReview> storyReviews) {
		Boolean status = Boolean.FALSE;

		if (storyReviews != null) {
			try {
				for (StoryReview storyReview : storyReviews) {
					save(storyReview);
				}
				status = Boolean.TRUE;
			} catch (Exception exception) {
				LOGGER.debug("Exception: ", exception);
			}
		}

		return status;
	}

	public List<Question> getAllQuestions() {
		Query query = getDAOManager().createNamedQuery("findAllQuestions", Question.class);
		return (List<Question>) query.getResultList();
	}

	public List<StoryReview> getReviewsByAssignment(Long assingmentId) {
		Query query = getDAOManager().createQuery(
				"select r from storyReview r " + "where r.storyAssignmentId = ?1 and r.deleted=0");
		query.setParameter(1, assingmentId);
		return (List<StoryReview>) query.getResultList();
	}

}