package com.scholastic.intl.writingawards.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Student.class)
public abstract class Student_ extends com.scholastic.intl.writingawards.entity.CommonEntity_ {

	public static volatile SingularAttribute<Student, Long> id;
	public static volatile SingularAttribute<Student, String> refferalSources;
	public static volatile SingularAttribute<Student, School> school;
	public static volatile SingularAttribute<Student, String> parentEmail;
	public static volatile SingularAttribute<Student, Integer> age;
	public static volatile SingularAttribute<Student, Long> studentGroup;
	public static volatile SingularAttribute<Student, String> fullName;
	public static volatile SingularAttribute<Student, String> regNo;
	public static volatile SingularAttribute<Student, Integer> studentClass;
	public static volatile SingularAttribute<Student, String> parentPhone;
	public static volatile SingularAttribute<Student, String> parentName;

}

