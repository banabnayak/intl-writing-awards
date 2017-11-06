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
public class StateVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long stateId;
	private String stateName;
	private List<CityVO> cityList;

	/**
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(final Long stateId) {
		this.stateId = stateId;
	}
	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}
	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(final String stateName) {
		this.stateName = stateName;
	}
	/**
	 * @return the cityList
	 */
	public List<CityVO> getCityList() {
		return cityList;
	}
	/**
	 * @param cityList the cityList to set
	 */
	public void setCityList(final List<CityVO> cityList) {
		this.cityList = cityList;
	}
}
