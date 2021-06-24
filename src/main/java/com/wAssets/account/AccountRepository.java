package com.wAssets.account;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import com.wAssets.account.model.AccountModel;
import com.wAssets.common.Constant;
import com.wAssets.common.Utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountRepository {
	
	private final DatabaseClient client;
	
	public AccountRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}
	
	/**
	 * 계좌저장
	 * @param model
	 * @return
	 */
	public Mono<Integer> insertAccount(AccountModel model){
		
		StringBuilder query = new StringBuilder();
		query.append("/* AccountRepository.insertAccount */");
		query.append("INSERT INTO ACCOUNT(");
		query.append("USER_NO /* 사용자번호 */");
		query.append(", ACCT_SEQ /* 계좌순서 */");
		query.append(", ACCT_TGT_CD /* 계좌유형코드 */");
		query.append(", ACCT_DIV_CD /* 계좌구분코드 */");
		query.append(", ACCT_NUM /* 계좌번호 */");
		query.append(", ACCT_NM /* 계좌명 */");
		if(ObjectUtils.isEmpty(model.getFontClor()) == false){
			query.append(", FONT_CLOR /* 글자색 */");
		}
		if(ObjectUtils.isEmpty(model.getFontClor()) == false){
			query.append(", BKGD_CLOR /* 배경색 */");
		}
		query.append(", CRAT_DT /* 생성일자 */");
		query.append(", EPY_DT_USE_YN /* 만기일 사용여부 */");
		if(Constant.Y.equals(model.getEpyDtUseYn()) && ObjectUtils.isEmpty(model.getEpyDt()) == false) {
			query.append(", EPY_DT /* 만기일 */");
		}
		query.append(", USE_YN /* 사용여부 */");
		if(ObjectUtils.isEmpty(model.getRemark()) == false){			query.append(", REMARK /* 비고 */");
		}
		query.append(", REG_DTTM /* 등록일시 */");
		query.append(", MOD_DTTM /* 수정일시 */");
		query.append(")VALUES(");
		query.append(model.getUserNo());									//사용자시퀀스
		query.append(", " +  model.getAcctSeq());							//계좌순서
		query.append(", '" +  model.getAcctTgtCd() + "'");					//계좌유형코드
		query.append(", '" +  model.getAcctDivCd() + "'");					//계좌구분코드
		query.append(", '" +  model.getAcctNum() + "'");					//계좌번호
		query.append(", '" +  model.getAcctNm() + "'");						//계좌명
		if(ObjectUtils.isEmpty(model.getFontClor()) == false){
			query.append(", '" +  model.getFontClor() + "'");				//글자색
		}
		if(ObjectUtils.isEmpty(model.getBkgdClor()) == false){
			query.append(", '" +  model.getBkgdClor() + "'");				//배경색
		}
		query.append(", '" +  model.getCratDt() + "'");						//생성일자
		query.append(", '" +  model.getEpyDtUseYn().toUpperCase() + "'");	//만기일 사용여부
		if(Constant.Y.equals(model.getEpyDtUseYn()) && ObjectUtils.isEmpty(model.getEpyDt()) == false) {
			query.append(", " +  model.getEpyDt() + "'");					//만기일
		}
		query.append(", '" +  model.getUseYn().toUpperCase() + "'");		//사용여부
		if(ObjectUtils.isEmpty(model.getRemark()) == false){
			query.append(", '" +  model.getRemark() + "'");
		}
		query.append(", NOW()");											//등록일시
		query.append(", NOW())");											//수정일시
		
		return client.sql(query.toString()).fetch().rowsUpdated();
	}
	
	/**
	 * 계좌수정
	 * @param model
	 * @return
	 */
	public Mono<Integer> updateAccount(AccountModel model){
		
		StringBuilder query = new StringBuilder("/* AccountRepository.updateAccount */");
		query.append(" UPDATE ACCOUNT /* [계좌] */");
		query.append(" SET ACCT_SEQ = ").append(model.getAcctSeq());
		query.append(" , ACCT_TGT_CD = ").append("'").append(model.getAcctTgtCd()).append("'");
		query.append(" , ACCT_DIV_CD = ").append("'").append(model.getAcctDivCd()).append("'");
		query.append(" , ACCT_NUM = ").append("'").append(model.getAcctNum()).append("'");
		query.append(" , ACCT_NM = ").append("'").append(model.getAcctNm()).append("'");
		if(ObjectUtils.isEmpty(model.getFontClor()) == false){
			query.append(" , FONT_CLOR = ").append("'").append(model.getFontClor()).append("'");
		}
		if(ObjectUtils.isEmpty(model.getBkgdClor()) == false){
			query.append(" , BKGD_CLOR = ").append("'").append(model.getBkgdClor()).append("'");
		}
		query.append(" , CRAT_DT = ").append("'").append(model.getCratDt()).append("'");
		query.append(" , EPY_DT_USE_YN = ").append("'").append(model.getEpyDtUseYn()).append("'");
		query.append(" , EPY_DT = ").append("'").append(model.getEpyDt()).append("'");
		query.append(" , USE_YN = ").append("'").append(model.getUseYn()).append("'");
		query.append(" , EPY_DT_USE_YN = ").append("'").append(model.getEpyDtUseYn()).append("'");
		query.append(" , REMARK = ").append("'").append(model.getRemark()).append("'");
		query.append(" , MOD_DTTM = NOW()");
		query.append(" WHERE ACCT_IDX = ").append(model.getAcctIdx());
		query.append(" AND USER_NO = ").append(model.getUserNo());
		
		return client.sql(query.toString()).fetch().rowsUpdated();
	}
	
	/**
	 * 계좌삭제
	 * @param model
	 * @return
	 */
	public Mono<Integer> deleteAccount(AccountModel model){
		StringBuilder query = new StringBuilder();
		query.append("/* AccountRepository.deleteAccount */		");
		query.append(" DELETE FROM ACCOUNT /* [계좌] */");
		query.append(" WHERE USER_NO = ").append(model.getUserNo());
		query.append(" AND ACCT_IDX = ").append(model.getAcctIdx());
		
		return client.sql(query.toString()).fetch().rowsUpdated();
	}
	
	/**
	 * 계좌목록 조회
	 * @param params
	 * @param userNo
	 * @return
	 */
	public Flux<AccountModel> selectAccountList(MultiValueMap<String, String> params, String userNo){
		StringBuilder sql = new StringBuilder("/* AccountRepository.selectAccountList */ ");
		sql.append("SELECT ");
		sql.append("ACCT_IDX /* 계좌 인덱스 */");
		sql.append(",ACCT_SEQ /* 계좌순서 */");
		sql.append(",ACCT_TGT_CD /* 계좌유형코드 */");
		sql.append(",ACCT_DIV_CD /* 계좌구분코드 */");
		sql.append(",ACCT_NUM /* 계좌번호 */");
		sql.append(",ACCT_NM /* 계좌명 */");
		sql.append(",FONT_CLOR /* 글자색 */");
		sql.append(",BKGD_CLOR /* 배경색 */");
		sql.append(",CRAT_DT /* 생성일자 */");
		sql.append(",EPY_DT_USE_YN /* 만기일 사용여부 */");
		sql.append(",EPY_DT /* 만기일 */");
		sql.append(",USE_YN /* 사용여부 */");
		sql.append(",DATE_FORMAT(REG_DTTM, '%Y-%m-%d %H:%i:%S') AS REG_DTTM /* 등록일시 */");
		sql.append(",DATE_FORMAT(MOD_DTTM, '%Y-%m-%d %H:%i:%S') AS MOD_DTTM /* 수정일시 */");
		sql.append("FROM ACCOUNT /* [계좌] */ WHERE 1=1 AND USER_NO = ").append(userNo);
		
		return client.sql(sql.toString())
			.fetch()
			.all()
			.map(list -> Utils.converterMapToModel(list, AccountModel.class, true))
			.onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage(), error)));
	}
	
	/**
	 * 계좌정보 조회
	 * @param params
	 * @param userNo
	 * @return
	 */
	public Mono<AccountModel> selectAccount(MultiValueMap<String, String> params, String userNo){
		StringBuilder sql = new StringBuilder("/* AccountRepository.selectAccount */ ");
		sql.append("SELECT ");
		sql.append("ACCT_IDX ");
		sql.append(",ACCT_SEQ ");
		sql.append(",ACCT_TGT_CD ");
		sql.append(",ACCT_DIV_CD ");
		sql.append(",ACCT_NUM ");
		sql.append(",ACCT_NM ");
		sql.append(",FONT_CLOR ");
		sql.append(",BKGD_CLOR ");
		sql.append(",CRAT_DT ");
		sql.append(",EPY_DT_USE_YN ");
		sql.append(",EPY_DT ");
		sql.append(",USE_YN ");
		sql.append(",DATE_FORMAT(REG_DTTM, '%Y-%m-%d %H:%i:%S') AS REG_DTTM ");
		sql.append(",DATE_FORMAT(MOD_DTTM, '%Y-%m-%d %H:%i:%S') AS MOD_DTTM ");
		sql.append("FROM ACCOUNT WHERE 1=1 AND USER_SEQ = ").append(userNo);
		sql.append("AND ACCT_IDX = ").append(params.getFirst("acctIdx"));
		return client.sql(sql.toString())
			.fetch()
			.one()			
			.map(account -> Utils.converterMapToModel(account, AccountModel.class, true))
			.onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage(), error)));
	}
}
