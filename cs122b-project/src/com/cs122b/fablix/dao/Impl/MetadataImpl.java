package com.cs122b.fablix.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cs122b.fablix.dao.MetadataDao;
import com.cs122b.fablix.entity.vo.MetadataVo;

public class MetadataImpl implements MetadataDao {

	@Override
	public List<MetadataVo> selectMetadataByTable(String tableName) {
		List<MetadataVo> metadataVoList = new ArrayList<MetadataVo>();

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/moviedb");
			Connection dbcon = ds.getConnection();

			PreparedStatement pstmt = dbcon.prepareStatement(
					"select COLUMN_NAME, DATA_TYPE from information_schema.COLUMNS where table_name = ?");
			pstmt.setString(1, tableName);
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				MetadataVo metadataVo = new MetadataVo();
				metadataVo.setAttribute(result.getString(1));
				metadataVo.setType(result.getString(2));

				metadataVoList.add(metadataVo);
			}
			result.close();
			pstmt.close();
			dbcon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return metadataVoList;
	}
}
