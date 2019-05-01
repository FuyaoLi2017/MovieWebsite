package com.cs122b.fablix.service.Impl;

import java.util.ArrayList;
import java.util.List;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.dao.GenreDao;
import com.cs122b.fablix.dao.MovieDao;
import com.cs122b.fablix.dao.RatingDao;
import com.cs122b.fablix.dao.StarDao;
import com.cs122b.fablix.dao.Impl.GenreDaoImpl;
import com.cs122b.fablix.dao.Impl.MovieDaoImpl;
import com.cs122b.fablix.dao.Impl.RatingDaoImpl;
import com.cs122b.fablix.dao.Impl.StarDaoImpl;
import com.cs122b.fablix.entity.pojo.Genre;
import com.cs122b.fablix.entity.pojo.Movie;
import com.cs122b.fablix.entity.pojo.MovieWithRating;
import com.cs122b.fablix.entity.pojo.Rating;
import com.cs122b.fablix.entity.pojo.Star;
import com.cs122b.fablix.entity.vo.MovieListVo;
import com.cs122b.fablix.entity.vo.MovieVo;
import com.cs122b.fablix.service.BrowseMovieService;

public class BrowseMovieServiceImpl implements BrowseMovieService {

	private MovieDao movieDao = new MovieDaoImpl();
	private GenreDao genreDao = new GenreDaoImpl();
	private StarDao starDao = new StarDaoImpl();
	private RatingDao ratingdao = new RatingDaoImpl();

	@Override
	public ResponseModel<MovieListVo> listMoviesByGenre(String category, String sequenceBase, String ascOrDes,
			int limit, int offset) {
		// create a list of MovieVo to be the return type
		MovieListVo movieListVo = new MovieListVo();
		
		int numOfRecords;
		int numOfPages;
		List<MovieVo> movieVosList = new ArrayList<MovieVo>();
		
		// sorting by title or year
		if (sequenceBase.equals("title") || sequenceBase.equals("year")) {
			List<Movie> moviesList = movieDao.browseMovieByGenre(category, sequenceBase, ascOrDes, limit, offset);
			for (Movie movie : moviesList) {
				MovieVo movieVo = new MovieVo();

				List<Genre> genresList = genreDao.selectGenresByMovieId(movie.getId());
				List<Star> starsList = starDao.selectStarsByMovieId(movie.getId());
				Rating rating = ratingdao.selectRatingByMovieId(movie.getId());

				generateMovieVo(movieVo, movie, genresList, starsList, rating);
				movieVosList.add(movieVo);
			}
			numOfRecords = movieDao.selectNumOfAllRecordsByBrowsing("genre", category);
			numOfPages = generatePageNumber(numOfRecords, limit);
			
			movieListVo.setNumOfRecords(numOfRecords);
			movieListVo.setNumOfPages(numOfPages);
			movieListVo.setMovieVoList(movieVosList);
		} else { // implement the sorting by rating
			List<MovieWithRating> movieWithRatingList = new ArrayList<>();
			movieWithRatingList = movieDao.browsingMoviesWithRating("genre", category, sequenceBase,
					ascOrDes, limit, offset);
			for (MovieWithRating movieWithRating : movieWithRatingList) {
				MovieVo movieVo = new MovieVo();
				List<Genre> genresList = genreDao.selectGenresByMovieId(movieWithRating.getId());
				List<Star> starsList = starDao.selectStarsByMovieId(movieWithRating.getId());

				movieVo.setId(movieWithRating.getId());
				movieVo.setTitle(movieWithRating.getTitle());
				movieVo.setYear(movieWithRating.getYear());
				movieVo.setDirector(movieWithRating.getDirector());
				movieVo.setGenresList(genresList);
				movieVo.setStarsList(starsList);
				movieVo.setRating(movieWithRating.getRating());

				movieVosList.add(movieVo);
			}
			numOfRecords = movieDao.selectNumOfAllRecordsByBrowsing("genre", category);
			numOfPages = generatePageNumber(numOfRecords, limit);
			
			movieListVo.setNumOfRecords(numOfRecords);
			movieListVo.setNumOfPages(numOfPages);
			movieListVo.setMovieVoList(movieVosList);
		}
		return ResponseModel.createBySuccess("return a movieVo List select by genre: " + category, movieListVo);
	}

	// browse by firstLetter
	@Override
	public ResponseModel<MovieListVo> listMoviesByTitle(String category, String sequenceBase, String ascOrDes,
			int limit, int offset) {
		// create a list of MovieVo to be the return type
		MovieListVo movieListVo = new MovieListVo();
		
		int numOfRecords;
		int numOfPages;
		List<MovieVo> movieVosList = new ArrayList<MovieVo>();

		// sorting with title or year
		if (sequenceBase.equals("title") || sequenceBase.equals("year")) {
			List<Movie> moviesList = movieDao.browseMovieByFirstLetter(category, sequenceBase, ascOrDes, limit, offset);
			for (Movie movie : moviesList) {
				MovieVo movieVo = new MovieVo();

				List<Genre> genresList = genreDao.selectGenresByMovieId(movie.getId());
				List<Star> starsList = starDao.selectStarsByMovieId(movie.getId());
				Rating rating = ratingdao.selectRatingByMovieId(movie.getId());

				generateMovieVo(movieVo, movie, genresList, starsList, rating);
				movieVosList.add(movieVo);
			}
			numOfRecords = movieDao.selectNumOfAllRecordsByBrowsing("firstLetter", category);
			numOfPages = generatePageNumber(numOfRecords, limit);
			
			movieListVo.setNumOfRecords(numOfRecords);
			movieListVo.setNumOfPages(numOfPages);
			movieListVo.setMovieVoList(movieVosList);
		} else { // implement the sorting by rating
			List<MovieWithRating> movieWithRatingList = new ArrayList<>();
			movieWithRatingList = movieDao.browsingMoviesWithRating("firstLetter", category, sequenceBase,
					ascOrDes, limit, offset);
			for (MovieWithRating movieWithRating : movieWithRatingList) {
				MovieVo movieVo = new MovieVo();
				List<Genre> genresList = genreDao.selectGenresByMovieId(movieWithRating.getId());
				List<Star> starsList = starDao.selectStarsByMovieId(movieWithRating.getId());

				movieVo.setId(movieWithRating.getId());
				movieVo.setTitle(movieWithRating.getTitle());
				movieVo.setYear(movieWithRating.getYear());
				movieVo.setDirector(movieWithRating.getDirector());
				movieVo.setGenresList(genresList);
				movieVo.setStarsList(starsList);
				movieVo.setRating(movieWithRating.getRating());

				movieVosList.add(movieVo);
			}
			numOfRecords = movieDao.selectNumOfAllRecordsByBrowsing("firstLetter", category);
			numOfPages = generatePageNumber(numOfRecords, limit);
			
			movieListVo.setNumOfRecords(numOfRecords);
			movieListVo.setNumOfPages(numOfPages);
			movieListVo.setMovieVoList(movieVosList);
		}
		return ResponseModel.createBySuccess("return a movieVo List select by first Letter: " + category,
				movieListVo);
	}

	private void generateMovieVo(MovieVo movieVo, Movie movie, List<Genre> genresList, List<Star> starsList,
			Rating rating) {
		movieVo.setId(movie.getId());
		movieVo.setTitle(movie.getTitle());
		movieVo.setYear(movie.getYear());
		movieVo.setDirector(movie.getDirector());
		movieVo.setGenresList(genresList);
		movieVo.setStarsList(starsList);
		movieVo.setRating(rating);
	}
	
	private int generatePageNumber(int numOfRecords, int limit) {
		int temp = numOfRecords / limit;
		return (limit * temp == numOfRecords) ? temp : temp + 1;
	}
}