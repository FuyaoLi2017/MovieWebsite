package com.cs122b.fablix.service;

import com.cs122b.fablix.entity.pojo.SalesRecord;

public interface SalesService {

	SalesRecord updateSalesRecord(int id, String movieId, String title, int quantity);

}
