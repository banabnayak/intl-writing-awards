package com.scholastic.intl.writingawards.model;

import java.io.Serializable;

/**
 * @author madhusmita.nayak
 *
 */
public class SchoolReportVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String city;
	private String state;
	private Integer pincode;
	private String principalName;
	private String principalEmail;
	private String phone;
	
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(final String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(final String state) {
		this.state = state;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(final Integer pincode) {
		this.pincode = pincode;
	}
	public String getPrincipalName() {
		return principalName;
	}
	public void setPrincipalName(final String principalName) {
		this.principalName = principalName;
	}
	public String getPrincipalEmail() {
		return principalEmail;
	}
	public void setPrincipalEmail(final String principalEmail) {
		this.principalEmail = principalEmail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}
}