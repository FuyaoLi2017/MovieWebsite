package com.cs122b.fablix.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cs122b.fablix.dao.GenreDao;
import com.cs122b.fablix.entity.pojo.Genre;

public class GenreDaoImpl implements GenreDao {

	@Override
	public List<Genre> selectGenresByMovieId(String id) {
		List<Genre> genresList = new ArrayList<Genre>();
        List<Integer> genreIdsList = new ArrayList<Integer>();
		// select the genreId from the genres_in_movies
		// then find the real name of the genre title
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			
			String findGenreId = "Select * from genres_in_movies where movieId = ?";
			PreparedStatement pstmt = dbcon.prepareStatement(findGenreId);
            pstmt.setString(1, id);
            ResultSet result = pstmt.executeQuery();
            
            while (result.next()) {
            	// add the ids of required genres into the list
                genreIdsList.add(result.getInt(1));
            }

            for (Integer genreId : genreIdsList) {
            	// select out the name of the genres
                Genre genres = selectGenreById(genreId);
                genresList.add(genres);
            }
            result.close();
            pstmt.close();
            dbcon.close();
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return genresList;
	}

	private Genre selectGenreById(Integer genreId) {
		Genre genre = new Genre();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
			
			String findGenre = "Select * from genres where id = ?";
			PreparedStatement pstmt = dbcon.prepareStatement(findGenre);
            pstmt.setInt(1, genreId);
            ResultSet result = pstmt.executeQuery();

            while(result.next()) {
                genre.setId(result.getInt(1));
                genre.setName(result.getString(2));
            }
            result.close();
            pstmt.close();
            dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return genre;
	}

	@Override
	public List<Genre> selectAllGenres() {
		List<Genre> genresList = new ArrayList<>();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();

			String findGenre = "Select * from genres";
			PreparedStatement pstmt = dbcon.prepareStatement(findGenre);

          // Perform the query
          ResultSet result = pstmt.executeQuery();

            while(result.next()) {
            	Genre genre = new Genre();
                genre.setId(result.getInt(1));
                genre.setName(result.getString(2));
                
                genresList.add(genre);
            }
            result.close();
            pstmt.close();
            dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return genresList;
	}

	@Override
	public List<Genre> selectGenreBySubstring(String partialGenre) {
		List<Genre> genresList = new ArrayList<>();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();

			String findGenre = "Select * from genres where name LIKE \"%" + partialGenre + "%\"";
			PreparedStatement pstmt = dbcon.prepareStatement(findGenre);

          // Perform the query
          ResultSet result = pstmt.executeQuery();

            while(result.next()) {
            	Genre genre = new Genre();
                genre.setId(result.getInt(1));
                genre.setName(result.getString(2));
                
                genresList.add(genre);
            }
            result.close();
            pstmt.close();
            dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return genresList;
	}

	@Override
	public int selectGenreMaxId() {
		int maxId = 0;
        try {
        	Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
        	PreparedStatement pstmt = dbcon.prepareStatement("Select max(id) from genres");
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                maxId = result.getInt(1);
            }
            dbcon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
	}
}