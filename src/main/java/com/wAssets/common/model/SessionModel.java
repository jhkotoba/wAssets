package com.wAssets.common.model;

public class SessionModel {

	//로그인 여부
	private String loginYn;
	
	//사용자번호
	private String userNo;
	
	//사용자 아이디
	private String userId;
	
	public boolean isLogin() {
		return "Y".equals(this.loginYn);
	}

	public String getLoginYn() {
		return loginYn;
	}

	public void setLoginYn(String loginYn) {
		this.loginYn = loginYn;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}	
}
