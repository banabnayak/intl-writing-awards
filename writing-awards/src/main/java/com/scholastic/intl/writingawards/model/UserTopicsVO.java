/**
 * 
 */
package com.scholastic.intl.writingawards.model;

import java.io.Serializable;
import java.math.BigInteger;


public class UserTopicsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param topicId
	 */
	private BigInteger id;

	private String name;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AllTopicsVO [topicId=" + id + ", topicName=" + name + "]";
	}

}
