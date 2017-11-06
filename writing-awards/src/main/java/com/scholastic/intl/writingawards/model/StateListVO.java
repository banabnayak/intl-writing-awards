/**
 * 
 */
package com.scholastic.intl.writingawards.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author madhusmita.nayak
 *
 */
public class StateListVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<StateVO> stateList;
	/**
	 * @return the stateList
	 */
	public List<StateVO> getStateList() {
		return stateList;
	}
	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(final List<StateVO> stateList) {
		this.stateList = stateList;
	}
}
