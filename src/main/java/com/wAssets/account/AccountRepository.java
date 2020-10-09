package com.wAssets.account;

import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public class AccountRepository {
	
	private final DatabaseClient client;
	
	public AccountRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}
	
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
}
