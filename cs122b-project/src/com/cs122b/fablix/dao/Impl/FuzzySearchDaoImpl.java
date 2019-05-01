package com.cs122b.fablix.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cs122b.fablix.dao.FuzzySearchDao;
import com.cs122b.fablix.entity.pojo.Movie;

public class FuzzySearchDaoImpl implements FuzzySearchDao {

	@Override
	public List<Movie> listMoviesByFuzzySearch(String query, String sequenceBase, String ascOrDes, int limit, int offset) {
		List<Movie> moviesList = new ArrayList<Movie>();
		query = query.trim().toLowerCase();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			
			// we allow 25% of the letters are incorrect compared with the actual movie name
			int maxWrongLetter = (int) (query.length() * 0.33);
			
			String fuzzySearchQuery = "";
			if (sequenceBase.equals("title") || sequenceBase.equals("year")) {
				fuzzySearchQuery = "SELECT * FROM movies WHERE edrec(\"" + query + "\", lower(title), " + maxWrongLetter + ") = 1" + " ORDER BY " + sequenceBase + " " + ascOrDes + " LIMIT " + limit + " OFFSET " + offset;
				System.out.println("fuzzy search query string:" + fuzzySearchQuery);
			} else {
				fuzzySearchQuery = "SELECT * FROM movies as m left join ratings as r on m.id = r.movieId WHERE edrec(\"" + query + "\", lower(title), " + maxWrongLetter + ") = 1" + " ORDER BY " + sequenceBase + " " + ascOrDes + " LIMIT " + limit + " OFFSET " + offset;
				System.out.println("fuzzy search query string:" + fuzzySearchQuery);
			}
			
            PreparedStatement pstmt = dbcon.prepareStatement(fuzzySearchQuery);
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

}
