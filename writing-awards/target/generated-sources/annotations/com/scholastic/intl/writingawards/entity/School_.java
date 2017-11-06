package com.scholastic.intl.writingawards.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(School.class)
public abstract class School_ extends com.scholastic.intl.writingawards.entity.CommonEntity_ {

	public static volatile SingularAttribute<School, Integer> pincode;
	public static volatile SingularAttribute<School, Long> id;
	public static volatile SetAttribute<School, Student> students;
	public static volatile SingularAttribute<School, String> phone;
	public static volatile SingularAttribute<School, String> principalName;
	public static volatile SingularAttribute<School, String> address;
	public static volatile SingularAttribute<School, String> name;
	public static volatile SingularAttribute<School, String> state;
	public static volatile SingularAttribute<School, Boolean> networkSchool;
	public static volatile SingularAttribute<School, String> principalEmail;
	public static volatile SingularAttribute<School, String> city;

}

