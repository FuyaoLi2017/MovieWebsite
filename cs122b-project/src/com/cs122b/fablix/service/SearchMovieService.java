package com.cs122b.fablix.service;

import java.util.List;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.MovieWithRating;
import com.cs122b.fablix.entity.vo.AutoCompleteListVo;
import com.cs122b.fablix.entity.vo.AutoCompleteVo;
import com.cs122b.fablix.entity.vo.MovieListVo;
import com.cs122b.fablix.entity.vo.MovieVo;

public interface SearchMovieService {

	ResponseModel<MovieListVo> listMoviesBySearchingResult(String title, String year, String director, String star,
			String sequenceBase, String ascOrDes, int limit, int offset);

	ResponseModel<List<MovieVo>> searchingTopNMovies(int numOfMovies);

	ResponseModel<AutoCompleteListVo> listMoviesByFullTextSearch(String query, String sequenceBase, String ascOrDes, int limit, int offset);

}
