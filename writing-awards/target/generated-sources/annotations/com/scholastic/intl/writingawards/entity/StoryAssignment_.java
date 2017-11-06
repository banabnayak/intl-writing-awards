package com.scholastic.intl.writingawards.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StoryAssignment.class)
public abstract class StoryAssignment_ extends com.scholastic.intl.writingawards.entity.CommonEntity_ {

	public static volatile SingularAttribute<StoryAssignment, Long> id;
	public static volatile SingularAttribute<StoryAssignment, Integer> totalMarks;
	public static volatile SingularAttribute<StoryAssignment, Long> storyId;
	public static volatile SingularAttribute<StoryAssignment, Long> assignedTo;

}

