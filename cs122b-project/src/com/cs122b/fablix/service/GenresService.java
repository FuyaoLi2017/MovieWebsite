package com.cs122b.fablix.service;

import java.util.List;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.Genre;

public interface GenresService {

	ResponseModel<List<Genre>> selectAllGenres();

	ResponseModel<List<Genre>> selectGenreBySubstring(String partialGenre);

}
