package com.cs122b.fablix.service.Impl;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.dao.CardDao;
import com.cs122b.fablix.dao.Impl.CardDaoImpl;
import com.cs122b.fablix.entity.pojo.CreditCard;
import com.cs122b.fablix.service.CardService;

public class CardServiceImpl implements CardService {
	
	private CardDao cardDao = new CardDaoImpl();

	@Override
	public ResponseModel<CreditCard> selectCardById(String cardId) {
		return ResponseModel.createBySuccess("select card by id: " + cardId, cardDao.selectCardById(cardId));
	}
}
