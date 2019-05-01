package com.cs122b.fablix.service;

import java.util.List;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.Movie;
import com.cs122b.fablix.entity.vo.MovieVo;

public interface MovieService {

	ResponseModel<MovieVo> selectMovieByMovieId(String movieId);

	List<Movie> selectMovieByStarId(String starId);

	ResponseModel<String> addNewMovie(int type, String title, int year, String director, Integer genreId, String genreName, String starId,
			String starName, int birthYear);

	ResponseModel<Boolean> verifyMovieByProperties(String title, int year, String director);


}
