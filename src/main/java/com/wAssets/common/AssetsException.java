package com.wAssets.common;

public class AssetsException extends RuntimeException {
	
	private static final long serialVersionUID = -50065836274598690L;
	
	private String code;
	private Object data;
	
	public AssetsException(String code) {
		super(code);
		this.code = code;
		this.data = null;
	}
	
	public AssetsException(String code, Object data) {
		super(code);
		this.code = code;
		this.data = data;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public Object getData() {
		return this.data;
	}
}
