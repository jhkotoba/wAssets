package com.wAssets.common;

/**
 * 저장, 수정, 삭제 갯수를 저장하는 모델
 * @author JeHoon
 *
 */
public class ApplyModel {
	
	//저장 카운트
	private int insertCnt = 0;
	//수정 카운트
	private int updateCnt = 0;
	//삭제 카운트
	private int deleteCnt = 0;
	
	//기본 생성자
	public ApplyModel() {}
	//0이상 insert 0 update 0이하 delete
	public ApplyModel(int applyType, int count) {
		if(applyType > 0) {
			this.insertCnt = count;
		}else if(applyType == 0){
			this.updateCnt = count;
		}else {
			this.deleteCnt = count;
		}
	}
	
	//카운트값이 있는지 체크
	public boolean isCount() {
		if(this.insertCnt > 0) {
			return true;
		}else if(this.updateCnt > 0) {
			return true;
		}else if(this.deleteCnt > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	//인자로 받은 apply모델값들을 더한다. 
	public void append(ApplyModel apply) {
		this.insertCnt += apply.getInsertCnt();
		this.updateCnt += apply.getUpdateCnt();
		this.deleteCnt += apply.getDeleteCnt();
	}
	
	//저장 카운트 증가
	public void insertPlus() {
		this.insertCnt++;
	}
	
	//저장 카운트 감소
	public void insertMinus() {
		this.insertCnt--;
	}
	
	//수정 카운트 증가
	public void updatePlus() {
		this.updateCnt++;
	}
	
	//수정 카운트 감소
	public void updateMinus() {
		this.updateCnt--;
	}
	
	//삭제 카운트 증가
	public void deletePlus() {
		this.deleteCnt++;
	}
	
	//삭제 카운트 감소
	public void deleteMinus() {
		this.deleteCnt--;
	}
	
	//저장카운트 더함
	public void appendInsertCnt(int insertCnt) {
		this.insertCnt += insertCnt;
	}
	
	//수정카운트 더함
	public void appendUpdateCnt(int updateCnt) {
		this.updateCnt += updateCnt;
	}
	
	//삭제카운트 더함
	public void appendDeleteCnt(int deleteCnt) {
		this.deleteCnt += deleteCnt;
	}
	
	//get 저장카운트
	public int getInsertCnt() {
		return insertCnt;
	}

	//get 수정카운트
	public int getUpdateCnt() {
		return updateCnt;
	}
	
	//get 삭제카운트
	public int getDeleteCnt() {
		return deleteCnt;
	}
	
	//set 저장카운트
	public void setInsertCnt(int insertCnt) {
		this.insertCnt = insertCnt;
	}

	//set 수정카운트
	public void setUpdateCnt(int updateCnt) {
		this.updateCnt = updateCnt;
	}	

	//set 삭제카운트
	public void setDeleteCnt(int deleteCnt) {
		this.deleteCnt = deleteCnt;
	}
}
