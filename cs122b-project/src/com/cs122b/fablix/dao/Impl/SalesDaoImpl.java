package com.cs122b.fablix.dao.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cs122b.fablix.common.ResponseModel;
import com.cs122b.fablix.dao.SalesDao;
import com.cs122b.fablix.entity.pojo.Rating;
import com.cs122b.fablix.entity.pojo.SalesRecord;

public class SalesDaoImpl implements SalesDao {

	@Override
	public SalesRecord updateSalesRecord(int customerId, String movieId, String title, int quantity) {
		
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(new Date());
        SalesRecord salesRecord = new SalesRecord();
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/writedb");
            Connection dbcon = ds.getConnection();

            PreparedStatement pstmt = dbcon.prepareStatement("INSERT INTO sales (customerId, movieId, saleDate) VALUES (" + customerId + ", \"" + movieId + "\", \"" + date + "\")");
            
            // insert quantity lines of records to the database
            
            List<Integer> salesIdList = new ArrayList<>();
            int count = quantity;
            while (count > 0) {
            	pstmt.executeUpdate();
            	count--;
            	int id;
            	PreparedStatement selectLastSalesId = dbcon.prepareStatement("select max(id) from sales");
    			ResultSet result = selectLastSalesId.executeQuery();
    			
    			while (result.next()) {
                	id = result.getInt(1);
                	salesIdList.add(id);
                }
    			
    			result.close();
    			selectLastSalesId.close();
            }
            salesRecord.setMovieId(movieId);
            salesRecord.setTitle(title);
            salesRecord.setSalesIdList(salesIdList);
            salesRecord.setQuantity(quantity);
            pstmt.close();
            dbcon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesRecord;
	}

}
