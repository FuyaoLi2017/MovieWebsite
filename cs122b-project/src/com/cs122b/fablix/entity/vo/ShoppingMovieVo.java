package com.cs122b.fablix.entity.vo;

public class ShoppingMovieVo {

	private MovieVo movieVo;
	private int quantity;

	public ShoppingMovieVo(MovieVo movieVo, int quantity) {
		super();
		this.movieVo = movieVo;
		this.quantity = quantity;
	}

	public ShoppingMovieVo() {
		super();
	}

	public MovieVo getMovieVo() {
		return movieVo;
	}

	public void setMovieVo(MovieVo movieVo) {
		this.movieVo = movieVo;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
