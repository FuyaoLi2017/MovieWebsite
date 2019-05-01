package com.cs122b.fablix.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cs122b.fablix.dao.CardDao;
import com.cs122b.fablix.entity.pojo.CreditCard;
import com.cs122b.fablix.entity.pojo.Genre;

public class CardDaoImpl implements CardDao {

	@Override
	public CreditCard selectCardById(String cardId) {
		CreditCard creditCard = new CreditCard();
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();
          
			String findCreditCard = "Select * from creditcards where id = ?";

			PreparedStatement pstmt = dbcon.prepareStatement(findCreditCard);
			pstmt.setString(1, cardId);
			ResultSet result = pstmt.executeQuery();

            while(result.next()) {
            	creditCard.setId(result.getString(1));
            	creditCard.setFirstName(result.getString(2));
            	creditCard.setLastName(result.getString(3));
            	creditCard.setExpiration(result.getDate(4));
            }
            result.close();
            pstmt.close();
            dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return creditCard;
	}

}
