package com.cs122b.fablix.entity.pojo;

public class Star {

	private String id;

	private String name;

	private Integer birthYear;

	public Star(String id, String name, Integer birthYear) {
		this.id = id;
		this.name = name;
		this.birthYear = birthYear;
	}

	public Star() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

}
