package com.scholastic.intl.writingawards.model;

import java.io.Serializable;
import java.util.List;

import com.scholastic.intl.writingawards.entity.Group;

public class GroupVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Group> group;

	public List<Group> getGroup() {
		return group;
	}

	public void setGroup(List<Group> group) {
		this.group = group;
	}

}
