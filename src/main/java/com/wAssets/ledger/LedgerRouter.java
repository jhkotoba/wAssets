package com.wAssets.ledger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class LedgerRouter {
	
	/**
	 * 장부
	 * @param ledgerHandler
	 * @return
	 */
	@Bean
	public RouterFunction<ServerResponse> ledgerRoute(LedgerHandler ledgerHandler){
		return RouterFunctions
			//장부목록 조회				
			.route(RequestPredicates.GET("/api/assets/getLedgerList")
				.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), ledgerHandler::getLedgerList)
			//장부정보 조회
			.andRoute(RequestPredicates.GET("/api/assets/getLedger")
				.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), ledgerHandler::getLedger)
			//장부 기본저장
			.andRoute(RequestPredicates.POST("/api/assets/applyBasicLedger")
				.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), ledgerHandler::applyBasicLedger);
			
	}
}
