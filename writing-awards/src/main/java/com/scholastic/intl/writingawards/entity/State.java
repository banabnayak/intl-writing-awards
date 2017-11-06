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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name = "state")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "State.findAllStates", query = "select s from State s "
			+ "where s.deleted=0") })
public class State extends CommonEntity implements Serializable{
	
	private static final long serialVersionUID = 5229293218279281317L;
	public static final String FIND_ALL_STATE = "State.findAllStates";
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	@Column
	private String name;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference
	@JoinColumn(name="state_id")
	private Set<City> cities;
	
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
	 * @return the cities
	 */
	public Set<City> getCities() {
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public void setCities(final Set<City> cities) {
		this.cities = cities;
	}
}
