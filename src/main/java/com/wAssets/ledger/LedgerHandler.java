package com.wAssets.ledger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.wAssets.common.AssetsException;
import com.wAssets.common.CommonService;
import com.wAssets.common.Constant;
import com.wAssets.common.model.ResponseModel;
import com.wAssets.common.model.SessionModel;
import com.wAssets.ledger.model.LedgerModel;

import reactor.core.publisher.Mono;

@Component
public class LedgerHandler {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private LedgerService ledgerService;

	/**
	 * 장부목록 조회
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getLedgerList(ServerRequest request){
		//응답모델
		ResponseModel<List<LedgerModel>> result = new ResponseModel<List<LedgerModel>>();
		
		//로그인 정보체크
		Mono<SessionModel> mono = commonService.getSession(request).flatMap(session -> {
			if(session.isLogin()) {
				return Mono.just(session);
			}else {
				return Mono.error(new AssetsException(Constant.CODE_NO_LOGIN));
			}
		});
		
		return mono.flatMap(session -> {
			LedgerModel ledger = new LedgerModel();
			ledger.setUserNo(session.getUserNo());
			
			return ledgerService.getLedgerList(ledger).collectList();
		}).flatMap(ledList -> {
			result.setData(ledList);
			result.setResultCode(Constant.CODE_SUCCESS);
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(result));
		//정의된 응답오류
		}).onErrorResume(AssetsException.class, error -> {
			result.setResultCode(error.getCode());
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(result));
		//응답오류
		}).onErrorResume(error -> {
			result.setResultCode(error.getMessage());
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(result));
		});
	}
	
	/**
	 * 장부목록 조회
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getLedger(ServerRequest request){
		//응답모델
		ResponseModel<LedgerModel> result = new ResponseModel<LedgerModel>();		
		//로그인 정보체크
		Mono<SessionModel> mono = commonService.getSession(request).flatMap(session -> {
			if(session.isLogin()) {
				return Mono.just(session);
			}else {
				return Mono.error(new AssetsException(Constant.CODE_NO_LOGIN));
			}
		});
		
		return mono.flatMap(session -> {
			LedgerModel ledger = new LedgerModel();
			ledger.setUserNo(session.getUserNo());
			ledger.setLedIdx(Integer.parseInt(request.queryParams().getFirst("ledIdx")));
			
			return ledgerService.getLedger(ledger);
		}).flatMap(ledger -> {
			if(ledger == null) {
				result.setData(ledger);
				result.setResultCode(Constant.CODE_DATA_EMPTY);
				return ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
			}else {
				result.setData(ledger);
				result.setResultCode(Constant.CODE_SUCCESS);
				return ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
			}
		//정의된 응답오류
		}).onErrorResume(AssetsException.class, error -> {
			result.setResultCode(error.getCode());
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(result));
		//응답오류
		}).onErrorResume(error -> {
			result.setResultCode(error.getMessage());
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(result));
		});
	}
	
	/**
	 * 장부 기본정보 적용 (저장, 수정)
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> applyBasicLedger(ServerRequest request){
		//응답모델
		ResponseModel<LedgerModel> result = new ResponseModel<LedgerModel>();
		
		//로그인 정보체크
		Mono<SessionModel> mono = commonService.getSession(request).flatMap(session -> {
			if(session.isLogin()) {
				return Mono.just(session);
			}else {
				return Mono.error(new AssetsException(Constant.CODE_NO_LOGIN));
			}
		});
		
		return request.bodyToMono(LedgerModel.class)
			.zipWith(mono)
			.flatMap(tuple -> {
				//사용자번호 세팅
				tuple.getT1().setUserNo(tuple.getT2().getUserNo());
				//기본정보 저장
				return ledgerService.appayBasicLedger(tuple.getT1());
			}).flatMap(count -> {
				if(count > 0) {
					//성공
					result.setResultCode(Constant.CODE_SUCCESS);
					return ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
				}else {
					//insert 실패
					result.setResultCode(Constant.CODE_INSERT_EMPTY);
					return ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
				}
				//정의된 응답오류
			}).onErrorResume(AssetsException.class, error -> {
				result.setResultCode(error.getCode());
				return ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
			//응답오류
			}).onErrorResume(error -> {
				result.setResultCode(error.getMessage());
				return ServerResponse.ok()						
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
			});
	}
}
