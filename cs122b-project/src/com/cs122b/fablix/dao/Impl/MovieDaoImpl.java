package com.cs122b.fablix.dao.Impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import com.cs122b.fablix.dao.MovieDao;
import com.cs122b.fablix.entity.pojo.Movie;
import com.cs122b.fablix.entity.pojo.MovieWithRating;
import com.cs122b.fablix.entity.pojo.Rating;

public class MovieDaoImpl implements MovieDao {

	@Override
	public List<Movie> browseMovieByGenre(String category, String sequenceBase, String ascOrDes, int limit,
			int offset) {

		List<Movie> moviesList = new ArrayList<Movie>();

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			String findMovieByGenre = "SELECT m.id, m.title, m.year, m.director FROM movies as m, genres_in_movies as gm, genres as g "
					+ "WHERE g.id = gm.genreId AND m.id = gm.movieId AND g.name = \"" + category + "\" ORDER BY "
					+ sequenceBase + " " + ascOrDes + " LIMIT " + limit + " OFFSET " + offset;
			PreparedStatement pstmt = dbcon.prepareStatement(findMovieByGenre);
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				Movie movie = new Movie();
				movie.setId(result.getString(1));
				movie.setTitle(result.getString(2));
				movie.setYear(result.getInt(3));
				movie.setDirector(result.getString(4));
				moviesList.add(movie);
			}
			result.close();
			pstmt.close();
			dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moviesList;
	}

	@Override
	public List<Movie> browseMovieByFirstLetter(String category, String sequenceBase, String ascOrDes, int limit,
			int offset) {

		List<Movie> moviesList = new ArrayList<Movie>();

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();

			// match the first letter in the query
			category = category + "%";
			String findMovieByFirstLetter = "SELECT * from movies WHERE title LIKE \"" + category + "\" ORDER BY "
					+ sequenceBase + " " + ascOrDes + " LIMIT " + limit + " OFFSET " + offset;
			PreparedStatement pstmt = dbcon.prepareStatement(findMovieByFirstLetter);
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				Movie movie = new Movie();
				movie.setId(result.getString(1));
				movie.setTitle(result.getString(2));
				movie.setYear(result.getInt(3));
				movie.setDirector(result.getString(4));
				moviesList.add(movie);
			}
			result.close();
			pstmt.close();
			dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moviesList;
	}

	@Override
	public List<Movie> searchingMovies(String title, String year, String director, String star, String sequenceBase,
			String ascOrDes, int limit, int offset) {
		List<Movie> moviesList = new ArrayList<Movie>();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			String findMovies = "Select distinct m.id, m.title, m.year, m.director from movies m, stars_in_movies sm, stars s"
					+ " WHERE m.id = sm.movieId AND sm.starId = s.id AND m.title LIKE \"%" + title
					+ "%\" AND m.year LIKE \"%" + year + "%\" AND m.director LIKE \"%" + director
					+ "%\" AND s.name LIKE \"%" + star + "%\"" + " ORDER BY " + sequenceBase + " " + ascOrDes + " LIMIT " + limit
					+ " OFFSET " + offset;
			

			PreparedStatement pstmt = dbcon.prepareStatement(findMovies);
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				Movie movie = new Movie();
				movie.setId(result.getString(1));
				movie.setTitle(result.getString(2));
				movie.setYear(result.getInt(3));
				movie.setDirector(result.getString(4));
				moviesList.add(movie);
			}
			result.close();
			pstmt.close();
			dbcon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return moviesList;
	}

	@Override
	public Movie selectMovieByMovieId(String movieId) {

		Movie movie = new Movie();

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();

			String findMovie = "select * from movies where id = ?";
			PreparedStatement pstmt = dbcon.prepareStatement(findMovie);
			pstmt.setString(1, movieId);
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				movie.setId(result.getString(1));
				movie.setTitle(result.getString(2));
				movie.setYear(result.getInt(3));
				movie.setDirector(result.getString(4));
			}
			result.close();
			pstmt.close();
			dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movie;
	}

	@Override
	public List<MovieWithRating> searchingMoviesWithRating(String title, String year, String director, String star,
			String sequenceBase, String ascOrDes, int limit, int offset) {

		List<MovieWithRating> movieWithRatingList = new ArrayList<>();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			System.out.println("sequenceBase: " + sequenceBase);
			String findMovieVoWithRating = "SELECT distinct m.id, m.title, m.year, m.director, r.rating, r.numVotes from movies m,  stars_in_movies sm, stars s, "
					+ "(select movieId, rating, numVotes from ratings) as r where r.movieId = m.id AND m.id = sm.movieId AND sm.starId = s.id AND m.title LIKE \"%"
					+ title + "%\" AND m.year LIKE \"%" + year + "%\" AND m.director LIKE \"%" + director
					+ "%\" AND s.name LIKE \"%" + star + "%\"" + " order by " + sequenceBase + " " + ascOrDes
					+ " limit " + limit + " OFFSET " + offset;

			PreparedStatement pstmt = dbcon.prepareStatement(findMovieVoWithRating);
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				MovieWithRating movieWithRating = new MovieWithRating();
				movieWithRating.setId(result.getString(1));
				movieWithRating.setTitle(result.getString(2));
				movieWithRating.setYear(result.getInt(3));
				movieWithRating.setDirector(result.getString(4));

				Rating rating = new Rating();
				rating.setMovieId(result.getString(1));
				rating.setRating(result.getFloat(5));
				rating.setNumVotes(result.getInt(6));
				movieWithRating.setRating(rating);

				movieWithRatingList.add(movieWithRating);
			}
			result.close();
			pstmt.close();
			dbcon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieWithRatingList;
	}

	// realize sort with rating for browse function, support both genre and
	// firstLetter browse in this api
	@Override
	public List<MovieWithRating> browsingMoviesWithRating(String browseType, String category, String sequenceBase,
			String ascOrDes, int limit, int offset) {

		List<MovieWithRating> movieWithRatingList = new ArrayList<>();

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			// if browseType is genre
			if (browseType.equals("genre")) {
				String findMovieWithRatingList = "SELECT distinct m.id, m.title, m.year, m.director, r.rating, r.numVotes from movies m,  genres_in_movies as gm, genres as g, "
						+ "(select movieId, rating, numVotes from ratings) as r where r.movieId = m.id AND g.id = gm.genreId AND m.id = gm.movieId AND g.name = \""
						+ category + "\" order by " + sequenceBase + " " + ascOrDes + " limit " + limit + " OFFSET "
						+ offset;
				PreparedStatement pstmt = dbcon.prepareStatement(findMovieWithRatingList);
				ResultSet result = pstmt.executeQuery();

				while (result.next()) {
					MovieWithRating movieWithRating = new MovieWithRating();
					movieWithRating.setId(result.getString(1));
					movieWithRating.setTitle(result.getString(2));
					movieWithRating.setYear(result.getInt(3));
					movieWithRating.setDirector(result.getString(4));

					Rating rating = new Rating();
					rating.setMovieId(result.getString(1));
					rating.setRating(result.getFloat(5));
					rating.setNumVotes(result.getInt(6));
					movieWithRating.setRating(rating);

					movieWithRatingList.add(movieWithRating);
				}
				result.close();
				pstmt.close();
				dbcon.close();
			} else { // here browseType should be firstLetter
				category = category + "%";
				String findMovieWithRatingList = "SELECT distinct m.id, m.title, m.year, m.director, r.rating, r.numVotes from movies m, "
						+ "(select movieId, rating, numVotes from ratings) as r where r.movieId = m.id AND title LIKE \""
						+ category + "\" order by " + sequenceBase + " " + ascOrDes + " limit " + limit + " OFFSET "
						+ offset;
				System.out.println("firstLetter browse: " + findMovieWithRatingList);
				PreparedStatement pstmt = dbcon.prepareStatement(findMovieWithRatingList);
				ResultSet result = pstmt.executeQuery();

				while (result.next()) {
					MovieWithRating movieWithRating = new MovieWithRating();
					movieWithRating.setId(result.getString(1));
					movieWithRating.setTitle(result.getString(2));
					movieWithRating.setYear(result.getInt(3));
					movieWithRating.setDirector(result.getString(4));

					Rating rating = new Rating();
					rating.setMovieId(result.getString(1));
					rating.setRating(result.getFloat(5));
					rating.setNumVotes(result.getInt(6));
					movieWithRating.setRating(rating);

					movieWithRatingList.add(movieWithRating);
				}
				result.close();
				pstmt.close();
				dbcon.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieWithRatingList;
	}

	@Override
	public int selectNumOfAllRecordsByBrowsing(String browseType, String category) {
		
		int numOfAllRecords = 0;

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();

			if (browseType.equals("genre")) {
				String findRecords = "SELECT count(distinct m.id) FROM movies as m, genres_in_movies as gm, genres as g "
				+ "WHERE g.id = gm.genreId AND m.id = gm.movieId AND g.name = \"" + category + "\"";
				PreparedStatement pstmt = dbcon.prepareStatement(findRecords);
				ResultSet result = pstmt.executeQuery();

				while (result.next()) {
					numOfAllRecords = result.getInt(1);
				}
				result.close();
				pstmt.close();
				dbcon.close();
			} else { // browseType should be firstLetter in this case
				category = category + "%";
				String findRecords = "SELECT count(distinct m.id) from movies as m WHERE title LIKE \"" + category + "\"";
				PreparedStatement pstmt = dbcon.prepareStatement(findRecords);
				ResultSet result = pstmt.executeQuery();

				while (result.next()) {
					numOfAllRecords = result.getInt(1);
				}
				result.close();
				pstmt.close();
				dbcon.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numOfAllRecords;
	}

	@Override
	public int selectNumOfAllRecordsBySearching(String title, String year, String director, String star) {
		
		int numOfRecords = 0;
		
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			String findRecords = "Select count(distinct m.id) from movies m, stars_in_movies sm, stars s"
					+ " WHERE m.id = sm.movieId AND sm.starId = s.id AND m.title LIKE \"%" + title
					+ "%\" AND m.year LIKE \"%" + year + "%\" AND m.director LIKE \"%" + director
					+ "%\" AND s.name LIKE \"%" + star + "%\"";
			
			PreparedStatement pstmt = dbcon.prepareStatement(findRecords);
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				numOfRecords = result.getInt(1);
			}
			result.close();
			pstmt.close();
			dbcon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return numOfRecords;
	}

	@Override
	public List<Movie> selectMovieByStarId(String starId) {
		
		List<Movie> moviesList = new ArrayList<>();
		
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			String findMoviesByStarId = "select distinct movieId, title, year, director from stars_in_movies as sin, movies as m where sin.movieId = m.id and sin.starId= ?";
			PreparedStatement pstmt = dbcon.prepareStatement(findMoviesByStarId);
			pstmt.setString(1, starId);
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				Movie movie = new Movie();
				movie.setId(result.getString(1));
				movie.setTitle(result.getString(2));
				movie.setYear(result.getInt(3));
				movie.setDirector(result.getString(4));
				
				moviesList.add(movie);
			}
			result.close();
			pstmt.close();
			dbcon.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return moviesList;
	}

	@Override
	public String SelectMovieMaxId() {
		String maxId = "";
        try {
        	Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
        	PreparedStatement pstmt = dbcon.prepareStatement("Select max(id) from movies");
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                maxId = result.getString(1);
            }
            result.close();
            pstmt.close();
            dbcon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
	}

	@Override
	public String addMovieByProcedure(int type, String movieId, String title, int year, String director, int existingGenre,
			int genreId, String genreName, int existingStar, String starId, String starName, int birthYear) {
		// use result as output parameter in the stored procedure
		// this can be used to test whether the movie has been sucessfully added
		String result = "";
        try {
        	Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/writedb");
			Connection dbcon = ds.getConnection();
            			
            if (birthYear == -1) {
                CallableStatement cstmt = dbcon.prepareCall("call add_movie(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, ?)");
                cstmt.setInt(1, type);
                cstmt.setString(2, movieId);
                cstmt.setString(3, title);
                cstmt.setInt(4, year);
                cstmt.setString(5, director);
                cstmt.setInt(6, existingGenre);
                cstmt.setInt(7, genreId);
                cstmt.setString(8, genreName);
                cstmt.setInt(9, existingStar);
                cstmt.setString(10, starId);
                cstmt.setString(11, starName);
                // the registered output parameter is the id of the new movie
                cstmt.registerOutParameter(12, Types.VARCHAR);
                cstmt.execute();
                result = cstmt.getString(12);
                cstmt.close();
            } else { 
                CallableStatement cstmt = dbcon.prepareCall("call add_movie(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                cstmt.setInt(1, type);
                cstmt.setString(2, movieId);
                cstmt.setString(3, title);
                cstmt.setInt(4, year);
                cstmt.setString(5, director);
                cstmt.setInt(6, existingGenre);
                cstmt.setInt(7, genreId);
                cstmt.setString(8, genreName);
                cstmt.setInt(9, existingStar);
                cstmt.setString(10, starId);
                cstmt.setString(11, starName);
                cstmt.setInt(12, birthYear);
                // the registered output parameter is the id of the new movie
                cstmt.registerOutParameter(13, Types.VARCHAR);
                cstmt.execute();
                // the registered output parameter is the id of the new movie
                result = cstmt.getString(13);
                cstmt.close();
            }
            dbcon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}

	@Override
	public boolean verifyMovieByProperties(String title, int year, String director) {
		boolean verifyResult = false;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			PreparedStatement pstmt = dbcon.prepareStatement("Select count(*) from movies where title = ? AND year = ? AND director = ?");
			pstmt.setString(1, title);
			pstmt.setInt(2, year);
			pstmt.setString(3, director);
            ResultSet result = pstmt.executeQuery();

            int totalNumber = 0;
            while (result.next()) {
            	totalNumber = result.getInt(1);
            }
            verifyResult = totalNumber == 0 ? false : true; 
            result.close();
            pstmt.close();
            dbcon.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verifyResult;
	}

	@Override
	public List<Movie> listMoviesByFullTextSearch(String query, String sequenceBase, String ascOrDes, int limit, int offset) {
		List<Movie> moviesList = new ArrayList<>();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			
			String[] queries = query.split(" ");
			
			StringBuilder sb = new StringBuilder();
			for (String q :queries) {
				sb.append("+" + q.trim() + "*").append(" ");
			}
			sb.deleteCharAt(sb.length() - 1);
			
			String fullTextSearchQuery = "";
			
			if (sequenceBase.equals("title") || sequenceBase.equals("year")) {
				fullTextSearchQuery = "SELECT * FROM movies as m WHERE MATCH (title) AGAINST (\"" + sb.toString() 
				+ "\" IN BOOLEAN MODE)" + " ORDER BY " + sequenceBase + " " + ascOrDes + " LIMIT " + limit + " OFFSET " + offset;
				System.out.println("full text search query:" + fullTextSearchQuery);
			} else { // order by rating
				fullTextSearchQuery = "SELECT * FROM movies as m left join ratings as r on m.id = r.movieId" + 
						" WHERE MATCH (title) AGAINST (\"" + sb.toString() 
				+ "\" IN BOOLEAN MODE)" + " ORDER BY " + sequenceBase + " " + ascOrDes + " LIMIT " + limit + " OFFSET " + offset;
				System.out.println("full text search query:" + fullTextSearchQuery);
			}
			
			
            PreparedStatement pstmt = dbcon.prepareStatement(fullTextSearchQuery);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                Movie movie = new Movie();
                movie.setId(result.getString(1));
                movie.setTitle(result.getString(2));
                movie.setYear(result.getInt(3));
                movie.setDirector(result.getString(4));
                moviesList.add(movie);
            }
			
            result.close();
            pstmt.close();
            dbcon.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moviesList;
	}

	@Override
	public int selectNumOfAllRecordsByFullTextSearching(String query, int sign) {
		int numOfRecords = 0;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();

			String SearchQuery = "";
			
			if (sign == 1) {
				int maxWrongLetter = (int) (query.length() * 0.33);
				SearchQuery = "SELECT count(*) FROM movies WHERE edrec(\"" + query + "\", lower(title), " + maxWrongLetter + ") = 1";
			} else {
				String[] queries = query.split(" ");
				
				StringBuilder sb = new StringBuilder();
				for (String q :queries) {
					sb.append("+" + q.trim() + "*").append(" ");
				}
				sb.deleteCharAt(sb.length() - 1);
				SearchQuery = "SELECT count(*) FROM movies WHERE MATCH (title) AGAINST (\"" + sb.toString() + "\" IN BOOLEAN MODE)";
			}
			
			
            PreparedStatement pstmt = dbcon.prepareStatement(SearchQuery);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
            	numOfRecords = result.getInt(1);
            }
			
            result.close();
            pstmt.close();
            dbcon.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numOfRecords;
	}

}
