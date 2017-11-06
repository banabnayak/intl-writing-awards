package com.scholastic.intl.writingawards.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StoryReview.class)
public abstract class StoryReview_ extends com.scholastic.intl.writingawards.entity.CommonEntity_ {

	public static volatile SingularAttribute<StoryReview, Long> id;
	public static volatile SingularAttribute<StoryReview, Long> questionId;
	public static volatile SingularAttribute<StoryReview, Integer> weightage;
	public static volatile SingularAttribute<StoryReview, Long> storyAssignmentId;

}

