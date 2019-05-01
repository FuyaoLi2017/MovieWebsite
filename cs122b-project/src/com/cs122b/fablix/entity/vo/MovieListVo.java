package com.cs122b.fablix.entity.vo;

import java.util.List;

public class MovieListVo {

	private int numOfRecords;
	private int numOfPages;
	private List<MovieVo> movieVoList;

	public MovieListVo(int numOfRecords, int numOfPages, List<MovieVo> movieVoList) {
		super();
		this.numOfRecords = numOfRecords;
		this.numOfPages = numOfPages;
		this.movieVoList = movieVoList;
	}

	public MovieListVo() {
		super();
	}

	public int getNumOfRecords() {
		return numOfRecords;
	}

	public void setNumOfRecords(int numOfRecords) {
		this.numOfRecords = numOfRecords;
	}

	public int getNumOfPages() {
		return numOfPages;
	}

	public void setNumOfPages(int numOfPages) {
		this.numOfPages = numOfPages;
	}

	public List<MovieVo> getMovieVoList() {
		return movieVoList;
	}

	public void setMovieVoList(List<MovieVo> movieVoList) {
		this.movieVoList = movieVoList;
	}
}
