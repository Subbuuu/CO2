package com.ghd.co2.dto;

public class ResponseDTO<T> {

	String message;
	T resultSet;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getResultSet() {
		return resultSet;
	}
	public void setResultSet(T resultSet) {
		this.resultSet = resultSet;
	}
	
}
