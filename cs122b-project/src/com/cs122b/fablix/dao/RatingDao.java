package com.cs122b.fablix.dao;

import java.util.List;

import com.cs122b.fablix.entity.pojo.Rating;

public interface RatingDao {

	Rating selectRatingByMovieId(String id);

	List<Rating> searchingTopNMovies(int numOfMovies);

}
