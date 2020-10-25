package com.wAssets.account;

public class AccountModel {
	
	//계좌 시퀀스
	private Integer acctSeq;
	
	//유저 시퀀스
	private Integer userSeq;	
	
	//계좌사용처코드
	private String acctTgtCd;
	
	//계좌구분코드
	private String acctDivCd;
	
	//계좌번호
	private String acctNum;
	
	//계좌명
	private String acctNm;
	
	//글자색상
	private String fontClor;
	
	//배경색상
	private String bkgdClor;
	
	//생성일
	private String cratDt;
	
	//만기일 사용여부	
	private String epyDtUseYn;
	
	//만기일
	private String epyDt;
	
	//사용여부
	private String useYn;
	
	//비고
	private String rmk;
	
	//등록일시
	private String regDttm;
	
	//수정일시
	private String modDttm;
	
	public Integer getAcctSeq() {
		return acctSeq;
	}

	public void setAcctSeq(Integer acctSeq) {
		this.acctSeq = acctSeq;
	}

	public Integer getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}

	public String getAcctTgtCd() {
		return acctTgtCd;
	}

	public void setAcctTgtCd(String acctTgtCd) {
		this.acctTgtCd = acctTgtCd;
	}

	public String getAcctDivCd() {
		return acctDivCd;
	}

	public void setAcctDivCd(String acctDivCd) {
		this.acctDivCd = acctDivCd;
	}

	public String getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getAcctNm() {
		return acctNm;
	}

	public void setAcctNm(String acctNm) {
		this.acctNm = acctNm;
	}

	public String getFontClor() {
		return fontClor;
	}

	public void setFontClor(String fontClor) {
		this.fontClor = fontClor;
	}

	public String getBkgdClor() {
		return bkgdClor;
	}

	public void setBkgdClor(String bkgdClor) {
		this.bkgdClor = bkgdClor;
	}

	public String getCratDt() {
		return cratDt;
	}

	public void setCratDt(String cratDt) {
		this.cratDt = cratDt;
	}

	public String getEpyDtUseYn() {
		return epyDtUseYn;
	}

	public void setEpyDtUseYn(String epyDtUseYn) {
		this.epyDtUseYn = epyDtUseYn;
	}

	public String getEpyDt() {
		return epyDt;
	}

	public void setEpyDt(String epyDt) {
		this.epyDt = epyDt;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String getRegDttm() {
		return regDttm;
	}

	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}

	public String getModDttm() {
		return modDttm;
	}

	public void setModDttm(String modDttm) {
		this.modDttm = modDttm;
	}

	@Override
	public String toString() {
		return "AccountModel [acctSeq=" + acctSeq + ", userSeq=" + userSeq + ", acctTgtCd=" + acctTgtCd + ", acctDivCd="
				+ acctDivCd + ", acctNum=" + acctNum + ", acctNm=" + acctNm + ", fontClor=" + fontClor + ", bkgdClor="
				+ bkgdClor + ", cratDt=" + cratDt + ", epyDtUseYn=" + epyDtUseYn + ", epyDt=" + epyDt + ", useYn="
				+ useYn + ", rmk=" + rmk + ", regDttm=" + regDttm + ", modDttm=" + modDttm + "]";
	}
}
	
	
