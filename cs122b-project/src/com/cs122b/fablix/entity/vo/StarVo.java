package com.cs122b.fablix.entity.vo;

import java.util.List;

import com.cs122b.fablix.entity.pojo.Movie;

public class StarVo {

	private String id;

	private String name;

	private Integer birthYear;

	private List<Movie> moviesList;

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

	public List<Movie> getMoviesList() {
		return moviesList;
	}

	public void setMoviesList(List<Movie> moviesList) {
		this.moviesList = moviesList;
	}
}
