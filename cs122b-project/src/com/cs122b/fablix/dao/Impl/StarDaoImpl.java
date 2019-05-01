package com.cs122b.fablix.dao.Impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cs122b.fablix.dao.StarDao;
import com.cs122b.fablix.entity.pojo.Genre;
import com.cs122b.fablix.entity.pojo.Star;

public class StarDaoImpl implements StarDao {

	@Override
	public List<Star> selectStarsByMovieId(String id) {
		
		List<Star> starsList = new ArrayList<>();
		Set<String> starIdsSet = new HashSet<String>();
		
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			String findStarsByMovieId = "Select starId from stars_in_movies where movieId = ?";
			PreparedStatement pstmt = dbcon.prepareStatement(findStarsByMovieId);
            pstmt.setString(1, id);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                starIdsSet.add(result.getString(1));
            }
            for (String starId : starIdsSet) {
                Star star = selectStarById(starId);
                starsList.add(star);
            }
            result.close();
            pstmt.close();
            dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return starsList;
	}

	@Override
	public Star selectStarById(String starId) {
		Star star = new Star();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			PreparedStatement pstmt = dbcon.prepareStatement("Select * from stars where id = ?");
            pstmt.setString(1, starId);
            ResultSet result = pstmt.executeQuery();

            while(result.next()) {
                star.setId(result.getString(1));
                star.setName(result.getString(2));
                star.setBirthYear(result.getInt(3));
            }
            result.close();
            pstmt.close();
            dbcon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return star;
	}

	@Override
	public int checkStarName(String starName) {
		int resultCount = 0;
        try {
        	Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
            
            PreparedStatement pstmt = dbcon.prepareStatement("select count(1) from stars where name = ?");
            pstmt.setString(1, starName);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                resultCount = result.getInt(1);
            }
            result.close();
            pstmt.close();
            dbcon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultCount;
	}

	@Override
	public String SelectStarMaxId() {
		String maxId = "";
        try {
        	Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
        	PreparedStatement pstmt = dbcon.prepareStatement("Select max(id) from stars");
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                maxId = result.getString(1);
            }
            dbcon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
	}

	@Override
	public boolean addNewStar(String newId, String starName, int birthYear) {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/writedb");
            Connection connection = ds.getConnection();
            
            PreparedStatement pstmt;
            if (birthYear != -1) {
                pstmt = connection.prepareStatement("INSERT INTO stars (id, name, birthYear) VALUES (?, ?, ?)");
                pstmt.setString(1, newId);
                pstmt.setString(2, starName);
                pstmt.setInt(3, birthYear);
            } else {
                pstmt = connection.prepareStatement("INSERT INTO stars (id, name, birthYear) VALUES (?, ?, NULL)");
                pstmt.setString(1, newId);
                pstmt.setString(2, starName);
            }
            pstmt.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
	}

	@Override
	public List<Star> selectStarBySubstring(String partialStarName) {
		List<Star> starsList = new ArrayList<>();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();

			String findGenre = "Select * from stars where name LIKE \"%" + partialStarName + "%\"";
			PreparedStatement pstmt = dbcon.prepareStatement(findGenre);

          // Perform the query
          ResultSet result = pstmt.executeQuery();

            while(result.next()) {
            	Star star = new Star();
            	star.setId(result.getString(1));
            	star.setName(result.getString(2));
            	star.setBirthYear(result.getInt(3));
                
            	starsList.add(star);
            }
            result.close();
            pstmt.close();
            dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return starsList;
	}
}
