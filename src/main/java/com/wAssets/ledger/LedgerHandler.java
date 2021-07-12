package com.wAssets.ledger;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class LedgerHandler {

	/**
	 * 장부목록 조회
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getLedgerList(ServerRequest request){
		return Mono.empty();
	}
}
