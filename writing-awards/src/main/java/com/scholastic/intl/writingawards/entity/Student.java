package com.scholastic.intl.writingawards.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
@Entity(name = "student")
@NamedQueries({ @NamedQuery(name = "findStudentByEmail",
		query = "select s from student s where s.parentEmail = :name and s.deleted=0") })
public class Student extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "full_name", length = 60)
	private String fullName;

	@Column(name = "parent_name")
	private String parentName;

	@Column(name = "parent_phone")
	private String parentPhone;

	@Column(name = "parent_email")
	private String parentEmail;

	@Column(length = 2)
	private Integer age;

	@Column(name = "student_class")
	private Integer studentClass;

	@Column(name = "student_group")
	private Long studentGroup;

	@Column(name = "reg_no")
	private String regNo;
	
	@Column(name = "refferal_sources")
	private String refferalSources;

	@JsonIgnore
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "School_ID")
	private School school;

	@JsonIgnore
	@Transient
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "assignedTo")
	private List<StoryAssignment> storyAssignment;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
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
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(final Integer age) {
		this.age = age;
	}

	/**
	 * @return the studentClass
	 */
	public Integer getStudentClass() {
		return studentClass;
	}

	/**
	 * @param studentClass the studentClass to set
	 */
	public void setStudentClass(final Integer studentClass) {
		this.studentClass = studentClass;
	}

	/**
	 * @return the studentGroup
	 */
	public Long getStudentGroup() {
		return studentGroup;
	}

	/**
	 * @param studentGroup the studentGroup to set
	 */
	public void setStudentGroup(final Long studentGroup) {
		this.studentGroup = studentGroup;
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
	 * @return the school
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(final School school) {
		this.school = school;
	}

	/**
	 * @return the storyAssignment
	 */
	public List<StoryAssignment> getStoryAssignment() {
		return storyAssignment;
	}

	/**
	 * @param storyAssignment the storyAssignment to set
	 */
	public void setStoryAssignment(final List<StoryAssignment> storyAssignment) {
		this.storyAssignment = storyAssignment;
	}

	/**
	 * @return the refferalSources
	 */
	public String getRefferalSources() {
		return refferalSources;
	}

	/**
	 * @param refferalSources the refferalSources to set
	 */
	public void setRefferalSources(final String refferalSources) {
		this.refferalSources = refferalSources;
	}

}
