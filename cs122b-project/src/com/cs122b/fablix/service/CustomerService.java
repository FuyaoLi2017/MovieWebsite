package com.cs122b.fablix.service;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.entity.vo.CustomerVo;

public interface CustomerService {
	
	ResponseModel<CustomerVo> customerLogin(String email, String password, String reCaptcha);

	ResponseModel<CustomerVo> mobileLogin(String email, String password);

}
