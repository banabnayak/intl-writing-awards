package com.scholastic.intl.writingawards.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserAudit.class)
public abstract class UserAudit_ extends com.scholastic.intl.writingawards.entity.CommonEntity_ {

	public static volatile SingularAttribute<UserAudit, Long> id;
	public static volatile SingularAttribute<UserAudit, Date> logintime;
	public static volatile SingularAttribute<UserAudit, Long> userId;

}

