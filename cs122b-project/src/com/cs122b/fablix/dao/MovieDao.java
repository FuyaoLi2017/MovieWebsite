package com.cs122b.fablix.dao;

import java.util.List;

import com.cs122b.fablix.entity.pojo.Movie;
import com.cs122b.fablix.entity.pojo.MovieWithRating;
import com.cs122b.fablix.entity.vo.MovieVo;

public interface MovieDao {

	List<Movie> browseMovieByGenre(String category, String sequenceBase, String ascOrDes, int limit, int offset);

	List<Movie> browseMovieByFirstLetter(String category, String sequenceBase, String ascOrDes, int limit, int offset);

	List<Movie> searchingMovies(String title, String year, String director, String star, String sequenceBase,
			String ascOrDes, int limit, int offset);

	Movie selectMovieByMovieId(String movieId);

	List<MovieWithRating> searchingMoviesWithRating(String title, String year, String director, String star,
			String sequenceBase, String ascOrDes, int limit, int offset);

	List<MovieWithRating> browsingMoviesWithRating(String browseType, String category, String sequenceBase,
			String ascOrDes, int limit, int offset);

	int selectNumOfAllRecordsByBrowsing(String browseType, String category);

	int selectNumOfAllRecordsBySearching(String title, String year, String director, String star);

	List<Movie> selectMovieByStarId(String starId);

	String SelectMovieMaxId();

	String addMovieByProcedure(int type, String movieId, String title, int year, String director, int existingGenre, int genreId, String genreName,
			int existingStar, String starId, String starName, int birthYear);

	boolean verifyMovieByProperties(String title, int year, String director);

	List<Movie> listMoviesByFullTextSearch(String query, String sequenceBase, String ascOrDes, int limit, int offset);

	int selectNumOfAllRecordsByFullTextSearching(String query, int sign);

}