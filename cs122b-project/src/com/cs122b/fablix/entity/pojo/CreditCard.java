package com.cs122b.fablix.entity.pojo;

import java.util.Date;

public class CreditCard {

	private String id;

	private String firstName;

	private String lastName;

	private Date expiration;

	public CreditCard(String id, String firstName, String lastName, Date expiration) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.expiration = expiration;
	}

	public CreditCard() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
}
