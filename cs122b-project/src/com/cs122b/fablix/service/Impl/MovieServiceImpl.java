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
import com.cs122b.fablix.entity.pojo.Rating;
import com.cs122b.fablix.entity.pojo.Star;
import com.cs122b.fablix.entity.vo.MovieVo;
import com.cs122b.fablix.service.MovieService;

public class MovieServiceImpl implements MovieService {
	
	private MovieDao movieDao = new MovieDaoImpl();
	private GenreDao genreDao = new GenreDaoImpl();
	private StarDao starDao = new StarDaoImpl();
	private RatingDao ratingdao = new RatingDaoImpl();

	@Override
	public ResponseModel<MovieVo> selectMovieByMovieId(String movieId) {
		
		MovieVo movieVo = new MovieVo();
		Movie movie = movieDao.selectMovieByMovieId(movieId);
		List<Genre> genresList = genreDao.selectGenresByMovieId(movie.getId());
		List<Star> starsList = starDao.selectStarsByMovieId(movie.getId());
		Rating rating = ratingdao.selectRatingByMovieId(movie.getId());
		generateMovieVo(movieVo, movie, genresList, starsList, rating);
		return ResponseModel.createBySuccess("return a movieVo select by movieId: " + movieId, movieVo);
	}

	private void generateMovieVo(MovieVo movieVo, Movie movie, List<Genre> genresList, List<Star> starsList, Rating rating) {
		movieVo.setId(movie.getId());
		movieVo.setTitle(movie.getTitle());
		movieVo.setYear(movie.getYear());
        movieVo.setDirector(movie.getDirector());
        movieVo.setGenresList(genresList);
        movieVo.setStarsList(starsList);
        movieVo.setRating(rating);
	}

	@Override
	public List<Movie> selectMovieByStarId(String starId) {
		List<Movie> moviesList = new ArrayList<>();
		moviesList = movieDao.selectMovieByStarId(starId);
		return moviesList;
	}

	@Override
	public ResponseModel<String> addNewMovie(int type, String title, int year, String director, Integer genreId, String genreName,
			String starId, String starName, int birthYear) {
		
		// need to insert a genre which exists, default exists: 1, need to create a new genre: 0
		int existingGenre = 1;
		// need to insert a star which exists, default exists: 1, need to create a new star: 0
		int existingStar = 1;
		
		// generate a new genreId if needed
		if (genreId == null) {
			int maxGenreId = genreDao.selectGenreMaxId();
			genreId = maxGenreId + 1;
			existingGenre = 0;
		}
				
		// generate a new starId if needed
		if (starId == null) {
			String maxStarId = starDao.SelectStarMaxId();
	        int newStarId = Integer.valueOf(maxStarId.substring(2)) + 1;
	        starId = "nm" + newStarId;
	        existingStar = 0;
		}
		
        String maxMovieId = movieDao.SelectMovieMaxId();
        int newMovieId = Integer.valueOf(maxMovieId.substring(2)) + 1;
        String nextMovieId = "tt0" + newMovieId;

        System.out.println("nextStarId:" + starId + " nextMovieId:" + nextMovieId);
        System.out.println("existing genre:" + existingGenre + " existing star: " + existingStar);
        System.out.println("title:" + title + " year:" + year + " director:" + director + " genreName:" + genreName + " starName:" + starName + " birthYear:" + birthYear);
        String result = movieDao.addMovieByProcedure(type, nextMovieId, title, year, director, existingGenre, (int)genreId, genreName, existingStar, starId, starName, birthYear);
        return ResponseModel.createBySuccessMessage("create a new movie record, movieId: " + result);
	}

	@Override
	public ResponseModel<Boolean> verifyMovieByProperties(String title, int year, String director) {
		boolean result = movieDao.verifyMovieByProperties(title, year, director);
		if (result) {
			return ResponseModel.createBySuccessMessage("the movie record exist in the database");
		}
		return ResponseModel.createByErrorMessage("the movie record doesn't exist in the database");
	}
}
