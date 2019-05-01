package com.cs122b.fablix.entity.vo;

public class MetadataVo {

	private String attribute;

	private String type;

	public MetadataVo(String attribute, String type) {
		super();
		this.attribute = attribute;
		this.type = type;
	}

	public MetadataVo() {
		super();
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
