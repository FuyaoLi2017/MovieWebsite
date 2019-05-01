package com.cs122b.fablix.dao;

import java.util.List;

import com.cs122b.fablix.entity.pojo.Movie;

public interface FuzzySearchDao {

	List<Movie> listMoviesByFuzzySearch(String query, String sequenceBase, String ascOrDes, int limit, int offset);

}
