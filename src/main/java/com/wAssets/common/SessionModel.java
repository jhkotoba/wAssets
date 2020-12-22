package com.wAssets.common;

public class SessionModel {

	//로그인 여부
	private String loginYn;
	
	//사용자번호
	private Integer userSeq;
	
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

	public Integer getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}	
}
