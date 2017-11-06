package com.scholastic.intl.writingawards.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.User;

/****
 * 
 * @author shailu.chougale
 * 
 * @param <T>
 * @param <ID>
 */
@Stateless
@Default
public abstract class GenericDAOSupport<T, ID extends Serializable> implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(GenericDAOSupport.class);

	private Class<T> type;

	GenericDAOSupport() {
	}

	GenericDAOSupport(Class<T> type) {

		this.setType(type);
	}

	@Inject
	EntityManager entityManager;

	@Inject
	protected UserTransaction transaction;

	public EntityManager getDAOManager() {
		return entityManager;
	}

	/***
	 * 
	 * @param obj
	 */
	public void save(T obj) {

		try {
			//transaction.begin();
			entityManager.persist(obj);
			//transaction.commit();

		} catch (Exception exception) {
			if (LOG.isErrorEnabled()) {
				LOG.error(exception.getMessage());
			}
			

		}
	}

	/***
	 * 
	 * @param obj
	 */
	public void update(Object obj) {

		try {
			//transaction.begin();

			entityManager.merge(obj);

			//transaction.commit();

		} catch (Exception exception) {
			if (LOG.isErrorEnabled()) {
				LOG.error(exception.getMessage());
			}
			
		}
	}

	/***
	 * 
	 * @param obj
	 */
	public void delete(Object obj) {

		try {
		//	transaction.begin();

			if (obj instanceof User) {
				User user = (User) obj;
				entityManager.remove(user);
			}

		//	transaction.commit();

		} catch (Exception exception) {
			if (LOG.isErrorEnabled()) {
				LOG.error(exception.getMessage());
			}
			
		}
	}

	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

}
