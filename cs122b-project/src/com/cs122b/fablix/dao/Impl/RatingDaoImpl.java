package com.cs122b.fablix.dao.Impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cs122b.fablix.dao.RatingDao;
import com.cs122b.fablix.entity.pojo.Rating;

public class RatingDaoImpl implements RatingDao {

	@Override
	public Rating selectRatingByMovieId(String id) {
		Rating rating = new Rating();
        try {
        	Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
            PreparedStatement pstmt = dbcon.prepareStatement("select * from ratings where movieId = ? LIMIT 1");
            pstmt.setString(1, id);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                rating.setMovieId(result.getString(1));
                rating.setRating(result.getFloat(2));
                rating.setNumVotes(result.getInt(3));
            }
            result.close();
            pstmt.close();
            dbcon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rating;
	}

	@Override
	public List<Rating> searchingTopNMovies(int numOfMovies) {
		List<Rating> topRatingList = new ArrayList<>();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			String findTopNRating = "select * from ratings ORDER BY rating desc LIMIT " + numOfMovies;
			PreparedStatement pstmt = dbcon.prepareStatement(findTopNRating);
            ResultSet result = pstmt.executeQuery();
			
            while (result.next()) {
            	Rating rating = new Rating();
                rating.setMovieId(result.getString(1));
                rating.setRating(result.getFloat(2));
                rating.setNumVotes(result.getInt(3));
                
                topRatingList.add(rating);
            }
            result.close();
            pstmt.close();
            dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return topRatingList;
	}

}
