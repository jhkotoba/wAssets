package com.wAssets.account;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

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
		query.append("INSERT INTO ACCOUNT(");
		query.append("USER_SEQ /* 사용자시퀀스 */");
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
		if(ObjectUtils.isEmpty(model.getRemark()) == false){
			query.append(", REMARK /* 비고 */");
		}
		query.append(", REG_DTTM /* 등록일시 */");
		query.append(", MOD_DTTM /* 수정일시 */");
		query.append(")VALUES(");
		query.append(model.getUserSeq());									//사용자시퀀스
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
//		StringBuilder query = new StringBuilder();
//		query.append("/* insertAccount */		");
//		//query.append("INSERT INTO ACCOUNT(		");
//		//query.append("	USER_SEQ				");
//		query.append("	, ACCT_TGT_CD			");
//		query.append("	, ACCT_DIV_CD			");
//		query.append("	, ACCT_NUM				");
//		query.append("	, ACCT_NM				");
//		//query.append("	, FONT_CLOR				");
//		//query.append("	, BKGD_CLOR				");
//		query.append("	, CRAT_DT				");
//		query.append("	, EPY_DT_USE_YN			");
//		//query.append("	, EPY_DT				");
//		query.append("	, USE_YN				");
//		//query.append("	, REMARK				");
//		query.append("	, REG_DTTM				");
//		query.append("	, MOD_DTTM				");
//		query.append(")							");
//		query.append("VALUES(					");
//		query.append("	:userSeq				");
//		query.append("	, :acctTgtCd			");
//		query.append("	, :acctDivCd			");
//		query.append("	, :acctNum				");
//		query.append("	, :acctNm				");
//		//query.append("	, :fontClor				");
//		//query.append("	, :bkgdClor				");
//		query.append("	, :cratDt				");
//		query.append("	, :epyDtUseYn			");
//		//query.append("	, :epyDt				");
//		query.append("	, 'Y'					");
//		//query.append("	, :remark				");
//		query.append("	, NOW()					");
//		query.append("	, NOW()					");
//		query.append(")							");
//		
//		return client.sql(query.toString())
//			.bind("userSeq", model.getUserSeq())
//			.bind("acctTgtCd", model.getAcctTgtCd())
//			.bind("acctDivCd", model.getAcctDivCd())
//			.bind("acctNum", model.getAcctNum())
//			.bind("acctNm", model.getAcctNm())
//			//.bind("fontClor", model.getFontClor())
//			//.bind("bkgdClor", model.getBkgdClor())
//			.bind("cratDt", model.getCratDt())
//			.bind("epyDtUseYn", model.getEpyDtUseYn())
//			//.bind("epyDt", model.getEpyDt())
//			//.bind("useYn", model.getUseYn())
//			//.bind("remark", model.getRemark())
//			.fetch().rowsUpdated();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT 1 FROM ACCOUNT WHERE 1=0");
		return client.sql(query.toString()).fetch().rowsUpdated().switchIfEmpty(Mono.error(new Exception("TEST")));
	}
	
	/**
	 * 계좌수정
	 * @param model
	 * @return
	 */
	public Mono<Integer> deleteAccount(AccountModel model){
		StringBuilder query = new StringBuilder();
		query.append("/* insertAccount */		");
		query.append("INSERT INTO ACCOUNT(		");
		query.append("	USER_SEQ				");
		query.append("	, ACCT_TGT_CD			");
		query.append("	, ACCT_DIV_CD			");
		query.append("	, ACCT_NUM				");
		query.append("	, ACCT_NM				");
		//query.append("	, FONT_CLOR				");
		//query.append("	, BKGD_CLOR				");
		query.append("	, CRAT_DT				");
		query.append("	, EPY_DT_USE_YN			");
		//query.append("	, EPY_DT				");
		query.append("	, USE_YN				");
		//query.append("	, REMARK				");
		query.append("	, REG_DTTM				");
		query.append("	, MOD_DTTM				");
		query.append(")							");
		query.append("VALUES(					");
		query.append("	:userSeq				");
		query.append("	, :acctTgtCd			");
		query.append("	, :acctDivCd			");
		query.append("	, :acctNum				");
		query.append("	, :acctNm				");
		//query.append("	, :fontClor				");
		//query.append("	, :bkgdClor				");
		query.append("	, :cratDt				");
		query.append("	, :epyDtUseYn			");
		//query.append("	, :epyDt				");
		query.append("	, 'Y'					");
		//query.append("	, :remark				");
		query.append("	, NOW()					");
		query.append("	, NOW()					");
		query.append(")							");
		
		return client.sql(query.toString())
			.bind("userSeq", model.getUserSeq())
			.bind("acctTgtCd", model.getAcctTgtCd())
			.bind("acctDivCd", model.getAcctDivCd())
			.bind("acctNum", model.getAcctNum())
			.bind("acctNm", model.getAcctNm())
			//.bind("fontClor", model.getFontClor())
			//.bind("bkgdClor", model.getBkgdClor())
			.bind("cratDt", model.getCratDt())
			.bind("epyDtUseYn", model.getEpyDtUseYn())
			//.bind("epyDt", model.getEpyDt())
			//.bind("useYn", model.getUseYn())
			//.bind("remark", model.getRemark())
			.fetch().rowsUpdated();
	}

	/**
	 * 계좌목록 조회
	 * @param params
	 * @param userSeq
	 * @return
	 */
	public Flux<AccountModel> selectAccountList(MultiValueMap<String, String> params, Integer userSeq){
		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append("ACCT_SEQ ");
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
		sql.append("FROM ACCOUNT WHERE 1=1 AND USER_SEQ = ").append(userSeq);
		
		return client.sql(sql.toString())
			.fetch()
			.all()
			.map(list -> Utils.converterMapToModel(list, AccountModel.class, true))
			.onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage(), error)));
	}
	
	/**
	 * 계좌정보 조횐
	 * @param params
	 * @param userSeq
	 * @return
	 */
	public Mono<AccountModel> selectAccount(MultiValueMap<String, String> params, Integer userSeq){
		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append("ACCT_SEQ ");
		sql.append(" ,ACCT_TGT_CD ");
		sql.append(" ,ACCT_DIV_CD ");
		sql.append(" ,ACCT_NUM ");
		sql.append(" ,ACCT_NM ");
		sql.append(" ,FONT_CLOR ");
		sql.append(" ,BKGD_CLOR ");
		sql.append(" ,CRAT_DT ");
		sql.append(" ,EPY_DT_USE_YN ");
		sql.append(" ,EPY_DT ");
		sql.append(" ,USE_YN ");
		sql.append(" ,DATE_FORMAT(REG_DTTM, '%Y-%m-%d %H:%i:%S') AS REG_DTTM ");
		sql.append(" ,DATE_FORMAT(MOD_DTTM, '%Y-%m-%d %H:%i:%S') AS MOD_DTTM ");
		sql.append(" FROM ACCOUNT WHERE 1=1 AND USER_SEQ = ").append(userSeq);
		sql.append(" AND ACCT_SEQ = ").append(params.getFirst("acctSeq"));
		return client.sql(sql.toString())
			.fetch()
			.one()			
			.map(account -> Utils.converterMapToModel(account, AccountModel.class, true))
			.onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage(), error)));
	}
}
