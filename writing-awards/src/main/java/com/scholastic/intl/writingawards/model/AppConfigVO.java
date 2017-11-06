/**
 * 
 */
package com.scholastic.intl.writingawards.model;

import java.io.Serializable;

/**
 * @author madhusmita.nayak
 *
 */
public class AppConfigVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer topStories;
	private Integer topParticipants;

	/**
	 * @return the topParticipants
	 */
	public Integer getTopParticipants() {
		return topParticipants;
	}

	/**
	 * @param topParticipants the topParticipants to set
	 */
	public void setTopParticipants(final Integer topParticipants) {
		this.topParticipants = topParticipants;
	}
	/**
	 * @return the topStories
	 */
	public Integer getTopStories() {
		return topStories;
	}

	/**
	 * @param topStories the topStories to set
	 */
	public void setTopStories(final Integer topStories) {
		this.topStories = topStories;
	}

	
	

}
