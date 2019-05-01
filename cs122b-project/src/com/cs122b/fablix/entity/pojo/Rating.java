package com.cs122b.fablix.entity.pojo;

public class Rating {

	private String movieId;

	private float rating;

	private Integer numVotes;

	public Rating(String movieId, float rating, Integer numVotes) {
		this.movieId = movieId;
		this.rating = rating;
		this.numVotes = numVotes;
	}

	public Rating() {

	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Integer getNumVotes() {
		return numVotes;
	}

	public void setNumVotes(Integer numVotes) {
		this.numVotes = numVotes;
	}

}
