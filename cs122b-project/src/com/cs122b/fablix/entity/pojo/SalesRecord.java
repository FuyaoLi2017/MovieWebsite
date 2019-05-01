package com.cs122b.fablix.entity.pojo;

import java.util.List;

public class SalesRecord {

	private String movieId;
	private String title;
	private List<Integer> salesIdList;
	private int quantity;

	public SalesRecord(String movieId, String title, List<Integer> salesIdList, int quantity) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.salesIdList = salesIdList;
		this.quantity = quantity;
	}

	public SalesRecord() {
		super();
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Integer> getSalesIdList() {
		return salesIdList;
	}

	public void setSalesIdList(List<Integer> salesIdList) {
		this.salesIdList = salesIdList;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
