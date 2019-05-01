package com.cs122b.fablix.service.Impl;

import com.cs122b.fablix.dao.SalesDao;
import com.cs122b.fablix.dao.Impl.SalesDaoImpl;
import com.cs122b.fablix.entity.pojo.SalesRecord;
import com.cs122b.fablix.service.SalesService;

public class SalesServiceImpl implements SalesService {
	
	SalesDao salesDao = new SalesDaoImpl();

	@Override
	public SalesRecord updateSalesRecord(int customerId, String movieId, String title, int quantity) {
		
		return salesDao.updateSalesRecord(customerId, movieId, title, quantity);
		
	}
}
