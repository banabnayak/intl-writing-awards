package com.scholastic.intl.writingawards.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "city")
@XmlAccessorType(XmlAccessType.FIELD)
public class City extends CommonEntity implements Serializable{
	
	private static final long serialVersionUID = 5229293218279281317L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	
	@Column
	private String name;
	
	@JsonIgnore
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "state_id")
	private State state;
	
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {

		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(final State state) {
		this.state = state;
	}
}
