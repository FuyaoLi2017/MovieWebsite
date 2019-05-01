package com.cs122b.fablix.service;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.CreditCard;

public interface CardService {

	ResponseModel<CreditCard> selectCardById(String cardId);

}
