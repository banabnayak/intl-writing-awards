package com.scholastic.intl.writingawards.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmailMessage.class)
public abstract class EmailMessage_ {

	public static volatile SingularAttribute<EmailMessage, Integer> id;
	public static volatile SingularAttribute<EmailMessage, String> templateType;
	public static volatile SingularAttribute<EmailMessage, Date> ignoredDate;
	public static volatile SingularAttribute<EmailMessage, Date> generatedDate;
	public static volatile SingularAttribute<EmailMessage, String> subject;
	public static volatile SingularAttribute<EmailMessage, Long> userId;
	public static volatile SingularAttribute<EmailMessage, Date> sentDate;
	public static volatile SingularAttribute<EmailMessage, String> recipientAddress;
	public static volatile SingularAttribute<EmailMessage, String> htmlBody;

}

