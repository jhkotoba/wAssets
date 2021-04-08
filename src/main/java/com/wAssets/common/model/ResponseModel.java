package com.wAssets.common.model;

public class ResponseModel<T> {
	
	private T data;
	
	private String resultCode;
	
	public ResponseModel(){}
	
	public ResponseModel(String resultCode){
		this.resultCode = resultCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
}
