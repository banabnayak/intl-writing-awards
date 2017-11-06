/**
 * 
 */
package com.scholastic.intl.writingawards.dao.impl;

import org.hibernate.transform.ResultTransformer;

/**
 * @author madhusmita.nayak
 *
 */
public class DAOUtilty {
	public void applyTransformer(javax.persistence.Query jpaQuery, ResultTransformer transformer) {
        if (jpaQuery instanceof org.hibernate.ejb.QueryImpl<?>) {
                org.hibernate.ejb.QueryImpl<?> queryImpl = (org.hibernate.ejb.QueryImpl<?>) jpaQuery;
                org.hibernate.Query hibernateQuery = queryImpl.getHibernateQuery();
                hibernateQuery.setResultTransformer(transformer);
        } else {
                throw new IllegalArgumentException(
                                "You must use Hibernate as the JPAProvider");
        }
}	
}
