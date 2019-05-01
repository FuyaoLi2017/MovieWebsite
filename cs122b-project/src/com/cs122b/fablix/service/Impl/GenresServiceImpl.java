package com.cs122b.fablix.service.Impl;

import java.util.ArrayList;
import java.util.List;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.dao.GenreDao;
import com.cs122b.fablix.dao.Impl.GenreDaoImpl;
import com.cs122b.fablix.entity.pojo.Genre;
import com.cs122b.fablix.service.GenresService;

public class GenresServiceImpl implements GenresService {

	GenreDao genreDao = new GenreDaoImpl();
	
	@Override
	public ResponseModel<List<Genre>> selectAllGenres() {
		List<Genre> genresList = new ArrayList<>();
		genresList = genreDao.selectAllGenres();
		return ResponseModel.createBySuccess("return the list of all genres", genresList);
	}

	@Override
	public ResponseModel<List<Genre>> selectGenreBySubstring(String partialGenre) {
		List<Genre> genresList = new ArrayList<>();
		genresList = genreDao.selectGenreBySubstring(partialGenre);
		return ResponseModel.createBySuccess("return the list of all matched genres", genresList);
	}

}
