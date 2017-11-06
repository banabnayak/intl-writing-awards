/**
 * 
 */
package com.scholastic.intl.writingawards.model;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author madhusmita.nayak
 *
 */
public class StudentDetailsVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger studentId;
	private String fullName;
	private Short age;
	private String parentEmail;
	private String parentName;
	private String parentPhone;
	private String regNo;
	private String className;
	private String classDesc;
	private String groupName;
	private String groupDesc;
	private String address;
	private String city;
	private String schoolName;
	private String phone;
	private Integer pincode;
	private String principalEmail;
	private String principalName;
	private String state;
	private String topicName;
	/**
	 * @return the studentId
	 */
	public BigInteger getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(final BigInteger studentId) {
		this.studentId = studentId;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the age
	 */
	public Short getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(final Short age) {
		this.age = age;
	}
	/**
	 * @return the parentEmail
	 */
	public String getParentEmail() {
		return parentEmail;
	}
	/**
	 * @param parentEmail the parentEmail to set
	 */
	public void setParentEmail(final String parentEmail) {
		this.parentEmail = parentEmail;
	}
	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}
	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(final String parentName) {
		this.parentName = parentName;
	}
	/**
	 * @return the parentPhone
	 */
	public String getParentPhone() {
		return parentPhone;
	}
	/**
	 * @param parentPhone the parentPhone to set
	 */
	public void setParentPhone(final String parentPhone) {
		this.parentPhone = parentPhone;
	}
	/**
	 * @return the regNo
	 */
	public String getRegNo() {
		return regNo;
	}
	/**
	 * @param regNo the regNo to set
	 */
	public void setRegNo(final String regNo) {
		this.regNo = regNo;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(final String className) {
		this.className = className;
	}
	/**
	 * @return the classDesc
	 */
	public String getClassDesc() {
		return classDesc;
	}
	/**
	 * @param classDesc the classDesc to set
	 */
	public void setClassDesc(final String classDesc) {
		this.classDesc = classDesc;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(final String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the groupDesc
	 */
	public String getGroupDesc() {
		return groupDesc;
	}
	/**
	 * @param groupDesc the groupDesc to set
	 */
	public void setGroupDesc(final String groupDesc) {
		this.groupDesc = groupDesc;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(final String address) {
		this.address = address;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(final String city) {
		this.city = city;
	}
	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}
	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(final String schoolName) {
		this.schoolName = schoolName;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(final String phone) {
		this.phone = phone;
	}
	/**
	 * @return the pincode
	 */
	public Integer getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(final Integer pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the principalEmail
	 */
	public String getPrincipalEmail() {
		return principalEmail;
	}
	/**
	 * @param principalEmail the principalEmail to set
	 */
	public void setPrincipalEmail(final String principalEmail) {
		this.principalEmail = principalEmail;
	}
	/**
	 * @return the principalName
	 */
	public String getPrincipalName() {
		return principalName;
	}
	/**
	 * @param principalName the principalName to set
	 */
	public void setPrincipalName(final String principalName) {
		this.principalName = principalName;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(final String state) {
		this.state = state;
	}
	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}
	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(final String topicName) {
		this.topicName = topicName;
	}
}
