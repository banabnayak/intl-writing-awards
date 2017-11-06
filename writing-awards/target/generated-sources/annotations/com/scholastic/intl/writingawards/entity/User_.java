package com.scholastic.intl.writingawards.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.scholastic.intl.writingawards.entity.CommonEntity_ {

	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, Short> status;
	public static volatile SingularAttribute<User, String> userId;
	public static volatile SingularAttribute<User, String> role;
	public static volatile SingularAttribute<User, String> fullName;
	public static volatile SingularAttribute<User, String> regNo;
	public static volatile SingularAttribute<User, String> password;

}

