package com.scholastic.intl.writingawards.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommonEntity.class)
public abstract class CommonEntity_ {

	public static volatile SingularAttribute<CommonEntity, Long> createdBy;
	public static volatile SingularAttribute<CommonEntity, Date> updatedDate;
	public static volatile SingularAttribute<CommonEntity, Boolean> deleted;
	public static volatile SingularAttribute<CommonEntity, Date> createdDate;
	public static volatile SingularAttribute<CommonEntity, Long> updatedBy;

}

