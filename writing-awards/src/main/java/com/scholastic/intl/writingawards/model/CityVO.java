/**
 * 
 */
package com.scholastic.intl.writingawards.model;

import java.io.Serializable;
/**
 * @author madhusmita.nayak
 *
 */
public class CityVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cityId;
	private String cityName;
	/**
	 * @return the cityId
	 */
	public Long getCityId() {
		return cityId;
	}
	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(final Long cityId) {
		this.cityId = cityId;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}

}
