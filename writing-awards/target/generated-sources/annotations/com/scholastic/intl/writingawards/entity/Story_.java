package com.scholastic.intl.writingawards.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Story.class)
public abstract class Story_ extends com.scholastic.intl.writingawards.entity.CommonEntity_ {

	public static volatile SingularAttribute<Story, Topic> topic;
	public static volatile SingularAttribute<Story, Long> id;
	public static volatile SingularAttribute<Story, Date> submissionDt;
	public static volatile SingularAttribute<Story, String> title;
	public static volatile SingularAttribute<Story, Integer> totalMarks;
	public static volatile SingularAttribute<Story, Long> studentId;
	public static volatile SingularAttribute<Story, String> storyText;

}

