package com.scholastic.intl.writingawards.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Group.class)
public abstract class Group_ extends com.scholastic.intl.writingawards.entity.CommonEntity_ {

	public static volatile SetAttribute<Group, Class> classes;
	public static volatile SingularAttribute<Group, Long> id;
	public static volatile SetAttribute<Group, Topic> topics;
	public static volatile SingularAttribute<Group, String> description;
	public static volatile SingularAttribute<Group, String> name;

}

