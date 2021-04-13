package com.wAssets.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.wAssets.account.model.AccountModel;
import com.wAssets.common.CommonService;
import com.wAssets.common.Constant;
import com.wAssets.common.model.ApplyModel;
import com.wAssets.common.model.ResponseModel;

import reactor.core.publisher.Mono;

@Component
public class AccountHandler {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AccountService accountService;

	/**
	 * 계좌저장
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> saveAccount(ServerRequest request){
		
		ResponseModel<AccountModel> result = new ResponseModel<AccountModel>();
		
		return request.bodyToMono(AccountModel.class)
			//계좌 밸류데이션체크
			.flatMap(accountService::vaildAccount)
			//세션조회
			.zipWith(commonService.getSession(request))
			//계좌 저장
			.flatMap(tuple -> {
				//로그인 확인
				if(tuple.getT2().isLogin()) {
					AccountModel account = tuple.getT1();
					account.setUserSeq(tuple.getT2().getUserSeq());
					return accountService.insertAccount(account);
				}else {
					return Mono.error(new RuntimeException(Constant.CODE_NO_LOGIN));
				}
			})
			//성공확인
			.flatMap(count -> {				
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
			})
			.onErrorResume(error -> {
				result.setResultCode(error.getMessage());
				return ServerResponse.ok()						
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
			});
	}
	
	/**
	 * 계좌목록 조회
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getAccountList(ServerRequest request){
		
		//응답모델
		ResponseModel<List<AccountModel>> result = new ResponseModel<List<AccountModel>>();
		
		//세션조회
		return commonService.getSession(request)
			//계좌목록 조회
			.flatMap(sesson -> accountService.selectAccountList(request, sesson).collectList())
			//응답
			.flatMap(fm -> {
				//응답
				result.setData(fm);
				result.setResultCode(Constant.CODE_SUCCESS);
				return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(result));
			//응답오류
			}).onErrorResume(error -> {
				result.setData(new ArrayList<AccountModel>());
				result.setResultCode(error.getMessage());
				return ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
			});
	}
	
	/**
	 * 계좌정보 조회
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getAccount(ServerRequest request){
		
		//응답모델
		ResponseModel<AccountModel> result = new ResponseModel<AccountModel>();
		
		//세션조회
		return commonService.getSession(request)
			//계좌목록 조회
			.flatMap(sesson -> accountService.selectAccount(request, sesson))
			//응답
			.flatMap(account -> {
				//응답
				result.setData(account);
				result.setResultCode(Constant.CODE_SUCCESS);
				return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(result));
			//응답오류
			}).onErrorResume(error -> {
				result.setData(new AccountModel());
				result.setResultCode(error.getMessage());
				return ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
			});
	}
	
	/**
	 * 계좌변경사항 반영(저장, 수정, 삭제)
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> applyAccount(ServerRequest request){
		
		//응답모델
		ResponseModel<ApplyModel> result = new ResponseModel<ApplyModel>();
		
		//세션조회
		return commonService.getSession(request).flatMap(session -> {
			
			//로그인 체크
			if(session.isLogin() == false) {
				return Mono.error(new RuntimeException(Constant.CODE_NO_LOGIN));
			}
				
			return request.bodyToFlux(AccountModel.class).collectList()
				.flatMap(acctList -> accountService.applyAccount(acctList, session.getUserSeq()))
				.flatMap(apply -> {
					//응답
					result.setData(apply);
					result.setResultCode(Constant.CODE_SUCCESS);
					return ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
				//응답오류
				}).onErrorResume(error -> {
					result.setData(null);
					result.setResultCode(error.getMessage());
					return ServerResponse.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(BodyInserters.fromValue(result));
				});
		});
	}
}