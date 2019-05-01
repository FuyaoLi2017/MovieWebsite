package com.cs122b.fablix.dao;

import com.cs122b.fablix.entity.pojo.Customer;

public interface CustomerDao {
	
	int checkEmail(String email);

	Customer verifyLogin(String email, String password);
	
}
