package com.sap.javalotteryapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Participant {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String surname;
	private String iconUri;

	public Participant() {
	}

	public Participant(final String name, final String surname, final String iconUri) {
		this.name = name;
		this.surname = surname;
		this.iconUri = iconUri;
	}

	public String getIconUri() {
		return iconUri;
	}

	public void setIconUri(final String iconUri) {
		this.iconUri = iconUri;
	}

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

	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}
}
