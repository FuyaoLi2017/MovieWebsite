package com.cs122b.fablix.dao;

import com.cs122b.fablix.entity.pojo.CreditCard;

public interface CardDao {

	CreditCard selectCardById(String cardId);

}
