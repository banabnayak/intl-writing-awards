package com.scholastic.intl.writingawards.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Topic.class)
public abstract class Topic_ extends com.scholastic.intl.writingawards.entity.CommonEntity_ {

	public static volatile SingularAttribute<Topic, Long> id;
	public static volatile SingularAttribute<Topic, String> description;
	public static volatile SingularAttribute<Topic, String> name;
	public static volatile SingularAttribute<Topic, Group> group;

}

