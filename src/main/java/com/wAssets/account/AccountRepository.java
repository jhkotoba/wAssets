package com.wAssets.account;

import java.util.Map;

import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

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
		
		StringBuilder query = new StringBuilder("INSERT INTO ACCOUNT(");
		query.append("USER_SEQ, ACCT_TGT_CD, ACCT_DIV_CD, ACCT_NUM, ACCT_NM, FONT_CLOR, ");
		query.append("BKGD_CLOR, CRAT_DT, EPY_DT_USE_YN, EPY_DT, USE_YN, REG_DTTM, MOD_DTTM");
		query.append(") ");
		query.append("VALUES(");		
		query.append(":userSeq, ");
		query.append(":acctTgtCd, ");
		query.append(":acctDivCd, ");
		query.append(":acctNum, ");
		query.append(":acctNm, ");
		query.append(":fontClor, ");
		query.append(":bkgdClor, ");
		query.append(":cratDt, ");
		query.append(":epyDtUseYn, ");
		query.append(":epyDt, ");
		query.append(":useYn, ");
		query.append("NOW(), ");
		query.append("NOW() ");
		query.append(")");
		
		return client.execute(query.toString())
			.bind("userSeq", model.getUserSeq())
			.bind("acctTgtCd", model.getAcctTgtCd())
			.bind("acctDivCd", model.getAcctDivCd())
			.bind("acctNum", model.getAcctNum())
			.bind("acctNm", model.getAcctNm())
			.bind("fontClor", model.getFontClor())
			.bind("bkgdClor", model.getBkgdClor())
			.bind("cratDt", model.getCratDt())
			.bind("epyDtUseYn", model.getEpyDtUseYn())
			.bind("epyDt", model.getEpyDt())
			.bind("useYn", model.getUseYn()).fetch().rowsUpdated();
	}
	
//	public Flux<?> selectAccountList(AccountModel model){
	public Flux<Map<String, Object>> selectAccountList(){
		System.out.println("REP selectAccountList");
		StringBuilder sql = new StringBuilder("SELECT * FROM ACCOUNT WHERE 1=1");
		//sql.append(" AND USER_SEQ = 1");//.append(model.getUserSeq());
		
		System.out.println(sql.toString());
		
		return client.execute(sql.toString()).fetch().all().doOnNext(onNext -> System.out.println(onNext));
	}
	
}
