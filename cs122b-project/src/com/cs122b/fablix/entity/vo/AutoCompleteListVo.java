package com.cs122b.fablix.entity.vo;

import java.util.List;

public class AutoCompleteListVo {
	private int numOfRecords;
	private int numOfPages;
	private List<AutoCompleteVo> autoCompleteList;

	public int getNumOfRecords() {
		return numOfRecords;
	}

	public void setNumOfRecords(int numOfRecords) {
		this.numOfRecords = numOfRecords;
	}

	public int getNumOfPages() {
		return numOfPages;
	}

	public void setNumOfPages(int numOfPages) {
		this.numOfPages = numOfPages;
	}

	public List<AutoCompleteVo> getAutoCompleteList() {
		return autoCompleteList;
	}

	public void setAutoCompleteList(List<AutoCompleteVo> autoCompleteList) {
		this.autoCompleteList = autoCompleteList;
	}
}
