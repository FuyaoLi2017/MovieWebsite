package com.cs122b.fablix.service.Impl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.dao.FuzzySearchDao;
import com.cs122b.fablix.dao.GenreDao;
import com.cs122b.fablix.dao.MovieDao;
import com.cs122b.fablix.dao.RatingDao;
import com.cs122b.fablix.dao.StarDao;
import com.cs122b.fablix.dao.Impl.FuzzySearchDaoImpl;
import com.cs122b.fablix.dao.Impl.GenreDaoImpl;
import com.cs122b.fablix.dao.Impl.MovieDaoImpl;
import com.cs122b.fablix.dao.Impl.RatingDaoImpl;
import com.cs122b.fablix.dao.Impl.StarDaoImpl;
import com.cs122b.fablix.entity.pojo.Genre;
import com.cs122b.fablix.entity.pojo.Movie;
import com.cs122b.fablix.entity.pojo.MovieWithRating;
import com.cs122b.fablix.entity.pojo.Rating;
import com.cs122b.fablix.entity.pojo.Star;
import com.cs122b.fablix.entity.vo.AutoCompleteListVo;
import com.cs122b.fablix.entity.vo.AutoCompleteVo;
import com.cs122b.fablix.entity.vo.MovieListVo;
import com.cs122b.fablix.entity.vo.MovieVo;
import com.cs122b.fablix.service.SearchMovieService;

public class SearchMovieServiceImpl implements SearchMovieService {
	
	private MovieDao movieDao = new MovieDaoImpl();
	private GenreDao genreDao = new GenreDaoImpl();
	private StarDao starDao = new StarDaoImpl();
	private RatingDao ratingDao = new RatingDaoImpl();
	private FuzzySearchDao fuzzySearchDao = new FuzzySearchDaoImpl();

	@Override
	public ResponseModel<MovieListVo> listMoviesBySearchingResult(String title, String year, String director,
			String star, String sequenceBase, String ascOrDes, int limit, int offset) {

		// create a list of MovieVo to be the return type
		MovieListVo movieListVo = new MovieListVo();
		
		int numOfRecords;
		int numOfPages;
        List<MovieVo> movieVosList = new ArrayList<MovieVo>();
        
        if (sequenceBase.equals("title") || sequenceBase.equals("year")) {
        	List<Movie> moviesList = movieDao.searchingMovies(title, year, director, star, sequenceBase, ascOrDes, limit, offset);
            for (Movie movie : moviesList) {
                MovieVo movieVo = new MovieVo();
                List<Genre> genresList = genreDao.selectGenresByMovieId(movie.getId());
                List<Star> starsList = starDao.selectStarsByMovieId(movie.getId());
                Rating rating = ratingDao.selectRatingByMovieId(movie.getId());
    			
                // add the movieVo to the list
    			generateMovieVo(movieVo, movie, genresList, starsList, rating);

                movieVosList.add(movieVo);
            }
            numOfRecords = movieDao.selectNumOfAllRecordsBySearching(title, year, director, star);
			numOfPages = generatePageNumber(numOfRecords, limit);
			
			movieListVo.setNumOfRecords(numOfRecords);
			movieListVo.setNumOfPages(numOfPages);
			movieListVo.setMovieVoList(movieVosList);
        } else {  // handle the searching which can be sorted by rating
        	List<MovieWithRating> movieWithRatingList = new ArrayList<>();
        	movieWithRatingList = movieDao.searchingMoviesWithRating(title, year, director, star, sequenceBase, ascOrDes, limit, offset);
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
        	numOfRecords = movieDao.selectNumOfAllRecordsBySearching(title, year, director, star);
			numOfPages = generatePageNumber(numOfRecords, limit);
			
			movieListVo.setNumOfRecords(numOfRecords);
			movieListVo.setNumOfPages(numOfPages);
			movieListVo.setMovieVoList(movieVosList);
        }
        
        return ResponseModel.createBySuccess("search movie with title: " + title + ", year: " + year + ", director: " + director + ", star: " + star, movieListVo);
	}

	@Override
	public ResponseModel<List<MovieVo>> searchingTopNMovies(int numOfMovies) {
        List<MovieVo> movieVosList = new ArrayList<MovieVo>();
        
        List<Rating> topRatingList = ratingDao.searchingTopNMovies(numOfMovies);
        for (Rating rating : topRatingList) {
            MovieVo movieVo = new MovieVo();
            Movie movie = movieDao.selectMovieByMovieId(rating.getMovieId());
            List<Genre> genresList = genreDao.selectGenresByMovieId(rating.getMovieId());
            List<Star> starsList = starDao.selectStarsByMovieId(rating.getMovieId());

            // add the movieVo to the list
            generateMovieVo(movieVo, movie, genresList, starsList, rating);
            
            movieVosList.add(movieVo);
        }
        return ResponseModel.createBySuccess("search result for top " + numOfMovies + " rating movies", movieVosList);
	}
	


	@Override
	public ResponseModel<AutoCompleteListVo> listMoviesByFullTextSearch(String query, String sequenceBase, String ascOrDes, int limit, int offset) {
		AutoCompleteListVo autoCompleteListVo = new AutoCompleteListVo();
		
		int numOfRecords = 0;
		int numOfPages = 0;
		List<AutoCompleteVo> AutoCompleteList = new ArrayList<>();

		// find out the running time of the query
		int sign = 0;
        long startTime = System.nanoTime();
        
        // use join here to avoid more operations
        List<Movie> moviesList = movieDao.listMoviesByFullTextSearch(query, sequenceBase, ascOrDes, limit, offset);
        if (moviesList.isEmpty()) {
        	sign = 1;
            moviesList = fuzzySearchDao.listMoviesByFuzzySearch(query, sequenceBase, ascOrDes, limit, offset);
        }
        
        long jdbcEndTime = System.nanoTime();
        long jdbcTime = jdbcEndTime - startTime;
        
        
        System.out.println("time to fetch the jdbc result: " + jdbcTime / 1000000.0 + "ms");

        for (Movie movie : moviesList) {
        	AutoCompleteVo autoCompleteVo = new AutoCompleteVo();
        	MovieVo movieVo = new MovieVo();
            List<Genre> genresList = genreDao.selectGenresByMovieId(movie.getId());
            List<Star> starsList = starDao.selectStarsByMovieId(movie.getId());
            Rating rating = ratingDao.selectRatingByMovieId(movie.getId());

            //assemble
            movieVo.setId(movie.getId());
    		movieVo.setTitle(movie.getTitle());
    		movieVo.setYear(movie.getYear());
            movieVo.setDirector(movie.getDirector());
            movieVo.setGenresList(genresList);
            movieVo.setStarsList(starsList);
            movieVo.setRating(rating);
            
            autoCompleteVo.setValue(movie.getTitle());
            autoCompleteVo.setData(movieVo);
            AutoCompleteList.add(autoCompleteVo);
        }
        // sign: 0 -> full text search mode
        // sign: 1 -> fuzzy search mode
        numOfRecords = movieDao.selectNumOfAllRecordsByFullTextSearching(query, sign);
		numOfPages = generatePageNumber(numOfRecords, limit);
        autoCompleteListVo.setNumOfPages(numOfPages);
        autoCompleteListVo.setNumOfRecords(numOfRecords);
        autoCompleteListVo.setAutoCompleteList(AutoCompleteList);        
        
        
        long endServlet = System.nanoTime();
        long servletTime = endServlet - startTime;
        System.out.println("time to fetch the servlet result: " + servletTime / 1000000.0 + "ms");

        
        // save the ts: servlet time, and tj:jdbc for finding out the fulltextsearch result
        FileWriter fw = null;
        try {
        	// AWS
            File f=new File("/home/ubuntu/timeLog.txt");
        	// local machine
//        	File f=new File("/Users/lifuyao/Desktop/timeLog.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(jdbcTime + "," + servletTime);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (sign == 0) {
        	return ResponseModel.createBySuccess("return auto complete results, using full text search!" + " query: " + query 
        			+ ", sequenceBase: " + sequenceBase + ", ascOrDes: " + ascOrDes + ", limit: " + limit + ", offset: " + offset, autoCompleteListVo);
        } else {
        	return ResponseModel.createBySuccess("return auto complete results, using fuzzy search!" + " query: " + query 
        			+ ", sequenceBase: " + sequenceBase + ", ascOrDes: " + ascOrDes + ", limit: " + limit + ", offset: " + offset, autoCompleteListVo);
        }
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

	private int generatePageNumber(int numOfRecords, int limit) {
		int temp = numOfRecords / limit;
		return (limit * temp == numOfRecords) ? temp : temp + 1;
	}
}