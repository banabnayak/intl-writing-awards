package com.scholastic.intl.writingawards.dao.impl;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.User;

/***
 * 
 * @author shailu.chougale
 * 
 */

@Named
public class UserDAOImpl extends GenericDAOSupport<User, Long> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 */
	public UserDAOImpl() {
	}

	UserDAOImpl(Class<User> type) {
		super(type);
	}

	/**
	 * 
	 * @param id
	 * @return User entity
	 */
	public User getUser(final Long id) {
		User user = getDAOManager().find(User.class, id);
		return user;

	}

	/***
	 * 
	 * @param id
	 * @param password
	 * @return User Entity
	 */
	public User authenticate(final String UserId, final String password) {

		Query query = getDAOManager().createQuery(
				"from user u where u.userId=?1 and u.password=?2 and u.deleted=0");
		query.setParameter(1, UserId);
		query.setParameter(2, password);
		try {
			User user = (User) query.getSingleResult();
			return user;
		} catch (NoResultException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.info("User Not Found" + e);
			}
			return null;
		}
	}

	/***
	 * 
	 * @param id
	 * @param email
	 * @return User Entity
	 */
	public User findUserwithEmail(final String email) {

		Query query = getDAOManager().createQuery("from user u where  u.userId=?2 and u.deleted=0");

		query.setParameter(2, email);
		try {
			User user = (User) query.getSingleResult();
			return user;
		} catch (NoResultException e) {

			LOGGER.info("User Not Found" + e);
			return null;
		}

	}

}
