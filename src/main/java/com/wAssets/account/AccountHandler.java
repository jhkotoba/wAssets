package com.wAssets.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.wAssets.common.CommonService;
import com.wAssets.common.Constant;
import com.wAssets.common.ResponseModel;

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
			.flatMap(accountService::insertAccount)
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
					result.setResultCode(Constant.CODE_INSERT_EMPTY_ERROR);
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
}
