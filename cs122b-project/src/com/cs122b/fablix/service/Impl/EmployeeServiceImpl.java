package com.cs122b.fablix.service.Impl;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.dao.EmployeeDao;
import com.cs122b.fablix.dao.Impl.EmployeeDaoImpl;
import com.cs122b.fablix.entity.pojo.Employee;
import com.cs122b.fablix.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

	EmployeeDao employeeDao = new EmployeeDaoImpl();
	@Override
	public ResponseModel<Employee> adminLogin(String email, String password) {
		int validEmail = employeeDao.checkEmail(email);
		if (validEmail == 0) {
			// return a ResponseModel Object
			return ResponseModel.createByErrorMessage("email: " + email + " doesn't exist");
		}
		
		Employee employee = employeeDao.verifyLogin(email, password);
        if ("jdkafbNLKDFANF123JEFKLA3kdf".equals(employee.getFullname())) {
            return ResponseModel.createByErrorMessage("password incorrect");
        }
        
        return ResponseModel.createBySuccess("admin login successfully", employee);
	}

}
