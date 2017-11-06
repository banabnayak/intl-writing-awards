package com.scholastic.intl.writingawards.dao.impl;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import com.scholastic.intl.writingawards.dao.impl.GenericDAOSupport;
import com.scholastic.intl.writingawards.entity.State;
/**
 * @author madhusmita.nayak
 *
 */
@Stateless
public class StateDaoImpl extends GenericDAOSupport<State, Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<State> getAllStates() {
	final Query query = getDAOManager().createNamedQuery(State.FIND_ALL_STATE, State.class);
		final List<State> stateList = query.getResultList();
		return stateList;
	}
}
