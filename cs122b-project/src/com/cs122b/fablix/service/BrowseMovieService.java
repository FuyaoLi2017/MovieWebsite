package com.cs122b.fablix.service;

import java.util.List;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.vo.MovieListVo;
import com.cs122b.fablix.entity.vo.MovieVo;

public interface BrowseMovieService {

	ResponseModel<MovieListVo> listMoviesByGenre(String category, String sequenceBase, String ascOrDes, int limit, int offset);

	ResponseModel<MovieListVo> listMoviesByTitle(String category, String sequenceBase, String ascOrDes, int limit, int offset);
}