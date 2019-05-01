package com.cs122b.fablix.dao;

import com.cs122b.fablix.entity.pojo.Employee;

public interface EmployeeDao {

	int checkEmail(String email);

	Employee verifyLogin(String email, String password);

}
