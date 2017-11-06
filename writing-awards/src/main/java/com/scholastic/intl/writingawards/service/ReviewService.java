package com.scholastic.intl.writingawards.service;

import java.util.List;

import com.scholastic.intl.writingawards.entity.Question;
import com.scholastic.intl.writingawards.model.ReviewStoryVO;
import com.scholastic.intl.writingawards.model.ReviewVO;

public interface ReviewService {

	public Boolean saveReview(ReviewVO reviewVO,String userId);
	public List<ReviewStoryVO> getAssignments(String studentId);	
	public List<Question> getAllQuestions();

}
