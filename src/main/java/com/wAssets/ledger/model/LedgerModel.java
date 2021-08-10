package com.wAssets.ledger.model;

/**
 * 장부 모델
 * @author JeHoon
 *
 */
public class LedgerModel {
	
	//장부 인덱스
	private Integer ledIdx;
	
	//유저번호
	private String userNo;
	
	//장부타입 코드
	private String ledTpCd;
	
	//장부명
	private String ledNm;
	
	//사용여부
	private String useYn;
	
	//총액
	private Integer amount;
	
	//전체저장여부
	private String saveYn;
	
	//장부비고
	private String ledRmk;
	
	//등록일시
	private String insDttm;
	
	//수정일시
	private String uptDttm;

	public Integer getLedIdx() {
		return ledIdx;
	}

	public void setLedIdx(Integer ledIdx) {
		this.ledIdx = ledIdx;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getLedTpCd() {
		return ledTpCd;
	}

	public void setLedTpCd(String ledTpCd) {
		this.ledTpCd = ledTpCd;
	}

	public String getLedNm() {
		return ledNm;
	}

	public void setLedNm(String ledNm) {
		this.ledNm = ledNm;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getSaveYn() {
		return saveYn;
	}

	public void setSaveYn(String saveYn) {
		this.saveYn = saveYn;
	}

	public String getLedRmk() {
		return ledRmk;
	}

	public void setLedRmk(String ledRmk) {
		this.ledRmk = ledRmk;
	}

	public String getInsDttm() {
		return insDttm;
	}

	public void setInsDttm(String insDttm) {
		this.insDttm = insDttm;
	}

	public String getUptDttm() {
		return uptDttm;
	}

	public void setUptDttm(String uptDttm) {
		this.uptDttm = uptDttm;
	}
}
