package com.cs122b.fablix.common;

public enum ResponseStatus {
	
	SUCCESS("SUCCESS"),
	ERROR("ERROR");
	
	private final String description;

	private ResponseStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
