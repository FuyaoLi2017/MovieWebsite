package com.cs122b.fablix.dao;

import java.util.List;

import com.cs122b.fablix.entity.pojo.Star;


public interface StarDao {

	List<Star> selectStarsByMovieId(String id);

	Star selectStarById(String StarId);

	int checkStarName(String starName);

	String SelectStarMaxId();

	boolean addNewStar(String newId, String starName, int birthYear);

	List<Star> selectStarBySubstring(String partialStarName);

}
