/**
 * 
 */
package com.scholastic.intl.writingawards.model;

import java.io.Serializable;
/**
 * @author madhusmita.nayak
 *
 */
public class CommonVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param createdBy
	 */
	private Long createdBy;
	/**
	 * @param updatedBy
	 */
	private Long updatedBy;
	
	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}


	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(final Long createdBy) {
		this.createdBy = createdBy;
	}


	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}


	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(final Long updatedBy) {
		this.updatedBy = updatedBy;
	}


	
	
}
