package com.cs122b.fablix.service;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.pojo.Employee;

public interface EmployeeService {

	ResponseModel<Employee> adminLogin(String email, String password);

}
