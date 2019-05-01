package com.cs122b.fablix.service;

import java.util.List;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.Genre;
import com.cs122b.fablix.entity.pojo.Star;

public interface StarService {

	Star selectStarByStarId(String starId);

	ResponseModel<Star> addNewStar(String starName, int birthYear);

	ResponseModel<List<Star>> selectStarBySubstring(String partialStarName);

}
