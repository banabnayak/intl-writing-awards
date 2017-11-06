package com.scholastic.intl.writingawards.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity(name = "school")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "findSchoolByName",
				query = "select s from school s where s.name like :name and s.deleted=0"),
		@NamedQuery(name = "findSchool", query = "select s from school s where s.deleted=0"),
		@NamedQuery(name = "findSchoolByEmail",
				query = "select s from school s where s.principalEmail = :name and s.deleted=0") })
public class School extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String address;

	@Column
	private String city;

	@Column
	private String state;

	@Column
	private Integer pincode;

	@Column(name = "principal_name")
	private String principalName;

	@Column(name = "principal_email")
	private String principalEmail;

	@Column
	private String phone;

	@Column(name = "is_network_school")
	private Boolean networkSchool;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference
	Set<Student> students;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

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

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(final Set<Student> students) {
		this.students = students;
	}

	/**
	 * @return the networkSchool
	 */
	public Boolean isNetworkSchool() {
		return networkSchool;
	}

	/**
	 * @param networkSchool the networkSchool to set
	 */
	public void setNetworkSchool(final Boolean networkSchool) {
		this.networkSchool = networkSchool;
	}

}
