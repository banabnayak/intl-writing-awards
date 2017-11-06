package com.scholastic.intl.writingawards.serviceImpl;

import java.util.List;
import javax.inject.Inject;
import com.scholastic.intl.writingawards.dao.impl.StateDaoImpl;
import com.scholastic.intl.writingawards.entity.State;
import com.scholastic.intl.writingawards.service.StateService;

/**
 * @author madhusmita.nayak
 *
 */
public class StateServiceImpl implements StateService{
	@Inject
	StateDaoImpl stateDaoImpl;
	@Override
	public List<State> getAllStates() {
		return stateDaoImpl.getAllStates();
	}

}
