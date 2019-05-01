package com.cs122b.fablix.entity.vo;

import java.util.List;

public class DBTableVo {

	private String tableName;

	private List<MetadataVo> metadataVoList;

	public DBTableVo(String tableName, List<MetadataVo> metadataVoList) {
		super();
		this.tableName = tableName;
		this.metadataVoList = metadataVoList;
	}

	public DBTableVo() {
		super();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<MetadataVo> getMetadataVoList() {
		return metadataVoList;
	}

	public void setMetadataVoList(List<MetadataVo> metadataVoList) {
		this.metadataVoList = metadataVoList;
	}

}
