package com.scholastic.intl.writingawards.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ extends com.scholastic.intl.writingawards.entity.CommonEntity_ {

	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, Date> startDate;
	public static volatile SingularAttribute<Task, String> description;
	public static volatile SingularAttribute<Task, Date> endDate;

}

