package com.cs122b.fablix.dao;

import com.cs122b.fablix.entity.pojo.SalesRecord;

public interface SalesDao {

	SalesRecord updateSalesRecord(int customerId, String movieId, String title, int quantity);

}
