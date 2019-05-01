package com.cs122b.fablix.service.Impl;

import java.util.ArrayList;
import java.util.List;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.dao.StarDao;
import com.cs122b.fablix.dao.Impl.StarDaoImpl;
import com.cs122b.fablix.entity.pojo.Star;
import com.cs122b.fablix.service.StarService;

public class StarServiceImpl implements StarService {

	StarDao starDao = new StarDaoImpl();

	@Override
	public Star selectStarByStarId(String starId) {
		Star starProfile = starDao.selectStarById(starId);
		return starProfile;
	}

	@Override
	public ResponseModel<Star> addNewStar(String starName, int birthYear) {
		int resultValue = starDao.checkStarName(starName);
		if (resultValue > 0) {
			return ResponseModel.createByErrorMessage("name exists");
		} else {
			String maxStarId = starDao.SelectStarMaxId();
			int nid = Integer.valueOf(maxStarId.substring(2)) + 1;
			String newId = "nm" + nid;
			System.out.println("newId:" + newId + " name:" + starName + " birthYear:" + birthYear);

			boolean insertResult = starDao.addNewStar(newId, starName, birthYear);
			if (insertResult) {
				return ResponseModel.createBySuccessMessage("insert new star successfully");
			} else {
				return ResponseModel.createByErrorMessage("fail to insert");
			}

		}
	}

	@Override
	public ResponseModel<List<Star>> selectStarBySubstring(String partialStarName) {
		List<Star> starsList = starDao.selectStarBySubstring(partialStarName);
		return ResponseModel.createBySuccess("return a list of matched star names", starsList);
	}

}
