package com.cs122b.fablix.common;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cs122b.fablix.entity.vo.MovieVo;

// if the inside json object is null, the key will be deleted
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseModel<T> implements Serializable {

	private static final long serialVersionUID = -4832179691191684791L;
	
	private String status;
	private String message;
	private T data;
	
	private ResponseModel(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
	
    private ResponseModel(String status, String message) {
        this.status = status;
        this.message = message;
    }
    
    private ResponseModel(String status, T data) {
    	this.status = status;
        this.data = data;
    }
    
    private ResponseModel(String message) {
    	this.message = message;
    }
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	
	// handle situations when there are invalid inputs
	public static<T> ResponseModel<T> createByErrorMessage(String message) {
		return new ResponseModel<T>(ResponseStatus.ERROR.getDescription(), message);
	}

	public static<T> ResponseModel<T> createBySuccess(String message, T data) {
		return new ResponseModel<T>(ResponseStatus.SUCCESS.getDescription(), message, data);
	}

	public static<T> ResponseModel<T> createBySuccessMessage(String message) {
		return new ResponseModel<T>(ResponseStatus.SUCCESS.getDescription(), message);
	}
}
