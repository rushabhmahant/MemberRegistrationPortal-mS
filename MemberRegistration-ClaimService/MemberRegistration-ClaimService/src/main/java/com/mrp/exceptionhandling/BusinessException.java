package com.mrp.exceptionhandling;

import org.springframework.stereotype.Component;

@Component
public class BusinessException extends RuntimeException {
	
	private String errorCode;
	private String errorMessage;
	
	public BusinessException() {
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "BusinessException [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}
	
	

}


