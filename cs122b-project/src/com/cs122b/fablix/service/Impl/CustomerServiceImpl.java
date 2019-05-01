package com.cs122b.fablix.service.Impl;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.common.VerifyUtils;
import com.cs122b.fablix.dao.CustomerDao;
import com.cs122b.fablix.dao.Impl.CustomerDaoImpl;
import com.cs122b.fablix.entity.pojo.Customer;
import com.cs122b.fablix.entity.vo.CustomerVo;
import com.cs122b.fablix.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	
	private CustomerDao customerDao = new CustomerDaoImpl();

	@Override
	public ResponseModel<CustomerVo> customerLogin(String email, String password, String reCaptcha) {
		int validEmail = customerDao.checkEmail(email);
		if (validEmail == 0) {
			// return a ResponseModel Object
			return ResponseModel.createByErrorMessage("email: " + email + " doesn't exist");
		}
		
		Customer customer = customerDao.verifyLogin(email, password);
        if (customer.getId() == -1) {
            return ResponseModel.createByErrorMessage("password incorrect");
        }
        
      //recaptcha validation
        boolean valid = VerifyUtils.verify(reCaptcha);
        if (!valid) {
            return ResponseModel.createByErrorMessage("recaptcha wrong");
        }
        
        // add properties to the customerVo
        CustomerVo customerVo = new CustomerVo();
        customerVo.setId(customer.getId());
        customerVo.setFirstName(customer.getFirstName());
        customerVo.setLastName(customer.getLastName());
        customerVo.setAddress(customer.getAddress());
        customerVo.setEmail(customer.getEmail());
        
        return ResponseModel.createBySuccess("customer login successfully", customerVo);
	}

	@Override
	public ResponseModel<CustomerVo> mobileLogin(String email, String password) {
		int validEmail = customerDao.checkEmail(email);
		if (validEmail == 0) {
			// return a ResponseModel Object
			return ResponseModel.createByErrorMessage("email: " + email + " doesn't exist(mobile)");
		}
		
		Customer customer = customerDao.verifyLogin(email, password);
        if (customer.getId() == -1) {
            return ResponseModel.createByErrorMessage("password incorrect(mobile)");
        }
        
        // add properties to the customerVo
        CustomerVo customerVo = new CustomerVo();
        customerVo.setId(customer.getId());
        customerVo.setFirstName(customer.getFirstName());
        customerVo.setLastName(customer.getLastName());
        customerVo.setAddress(customer.getAddress());
        customerVo.setEmail(customer.getEmail());
        
        return ResponseModel.createBySuccess("customer login successfully(mobile)", customerVo);
	}

}
