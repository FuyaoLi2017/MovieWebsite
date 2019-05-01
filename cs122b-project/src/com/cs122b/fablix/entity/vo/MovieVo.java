package com.cs122b.fablix.entity.vo;

import java.util.List;

import com.cs122b.fablix.entity.pojo.Genre;
import com.cs122b.fablix.entity.pojo.Rating;
import com.cs122b.fablix.entity.pojo.Star;

// the fields on the movie List
public class MovieVo {

	private String id;

	private String title;

	private Integer year;

	private String director;

	private List<Genre> genresList;

	private List<Star> starsList;

	private Rating rating;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public List<Genre> getGenresList() {
		return genresList;
	}

	public void setGenresList(List<Genre> genresList) {
		this.genresList = genresList;
	}

	public List<Star> getStarsList() {
		return starsList;
	}

	public void setStarsList(List<Star> starsList) {
		this.starsList = starsList;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

}
