package com.cs122b.fablix.dao;

import java.util.List;

import com.cs122b.fablix.entity.pojo.Genre;


public interface GenreDao {

	List<Genre> selectGenresByMovieId(String id);

	List<Genre> selectAllGenres();

	List<Genre> selectGenreBySubstring(String partialGenre);

	int selectGenreMaxId();

}