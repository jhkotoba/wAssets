package com.wAssets.ledger;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.wAssets.common.CommonRepository;
import com.wAssets.common.Utils;
import com.wAssets.ledger.model.LedgerModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class LedgerRepository extends CommonRepository{
	
	private final DatabaseClient client;
	
	public LedgerRepository(DatabaseClient databaseClient) {
		this.client = databaseClient;
	}
	
	/**
	 * 장부목록 조회
	 * @param ledger
	 * @return
	 */
	public Mono<LedgerModel> selectLedger(LedgerModel ledger){
		StringBuilder query = new StringBuilder();
		query.append("/* LedgerRepository.selectLedger */");
		query.append("SELECT ");
		query.append("LED_IDX");
		query.append(", USER_NO");
		query.append(", LED_TP_CD");
		query.append(", LED_NM");
		query.append(", USE_YN");
		query.append(", AMOUNT");
		query.append(", SAVE_YN");
		query.append(", LED_RMK");
		query.append(", DATE_FORMAT(INS_DTTM, '%Y-%m-%d %H:%i:%S') AS INS_DTTM");
		query.append(", DATE_FORMAT(UPT_DTTM, '%Y-%m-%d %H:%i:%S') AS UPT_DTTM");
		query.append(this.FROM).append("LEDGER");
		query.append(this.WHERE).append("LED_IDX").append(this.EQUALS).append(ledger.getLedIdx());
		query.append(this.AND).append("USER_NO").append(this.EQUALS);
		query.append(this.SINGLE_QUOTE).append(ledger.getUserNo()).append(this.SINGLE_QUOTE);
		
		return client.sql(query.toString())
			.fetch()
			.one()
			.map(list -> Utils.converterMapToModel(list, LedgerModel.class, true))
			.onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage(), error)));
	}
	
	/**
	 * 장부목록 조회
	 * @param ledger
	 * @return
	 */
	public Flux<LedgerModel> selectLedgerList(LedgerModel ledger){
		StringBuilder query = new StringBuilder();
		query.append("/* LedgerRepository.selectLedgerList */");
		query.append("SELECT ");
		query.append("LED_IDX");
		query.append(", USER_NO");
		query.append(", LED_TP_CD");
		query.append(", LED_NM");
		query.append(", USE_YN");
		query.append(", AMOUNT");
		query.append(", SAVE_YN");
		query.append(", LED_RMK");
		query.append(", DATE_FORMAT(INS_DTTM, '%Y-%m-%d %H:%i:%S') AS INS_DTTM");
		query.append(", DATE_FORMAT(UPT_DTTM, '%Y-%m-%d %H:%i:%S') AS UPT_DTTM");
		query.append(this.FROM).append("LEDGER");
		query.append(this.WHERE).append("USER_NO").append(this.EQUALS);
		query.append(this.SINGLE_QUOTE).append(ledger.getUserNo()).append(this.SINGLE_QUOTE);
		
		return client.sql(query.toString())
			.fetch()
			.all()
			.map(list -> Utils.converterMapToModel(list, LedgerModel.class, true))
			.onErrorResume(error -> Flux.error(new RuntimeException(error.getMessage(), error)));
	}
	
	/**
	 * 장부 저장
	 * @param ledger
	 * @return
	 */
	public Mono<Integer> insertLedger(LedgerModel ledger){
		
		StringBuilder query = new StringBuilder();
		query.append("/* LedgerRepository.insertLedger */");
		query.append("INSERT INTO LEDGER(USER_NO, LED_TP_CD, LED_NM, USE_YN, AMOUNT, LED_RMK, INS_DTTM, UPT_DTTM)");
		query.append(this.VALUES).append(this.LEFT_PARENTHESIS);
		query.append(this.SINGLE_QUOTE).append(ledger.getUserNo()).append(this.SINGLE_QUOTE);
		query.append(this.COMMA).append(this.SINGLE_QUOTE).append(ledger.getLedTpCd()).append(this.SINGLE_QUOTE);
		query.append(this.COMMA).append(this.SINGLE_QUOTE).append(ledger.getLedNm()).append(this.SINGLE_QUOTE);
		query.append(this.COMMA).append(this.N);
		query.append(this.COMMA).append(0);
		query.append(this.COMMA).append(this.SINGLE_QUOTE).append(ledger.getLedRmk()).append(this.SINGLE_QUOTE);
		query.append(this.COMMA).append(this.NOW);
		query.append(this.COMMA).append(this.NOW);
		query.append(this.RIGHT_PARENTHESIS);
		
		return client.sql(query.toString()).fetch().rowsUpdated();
	}
}

