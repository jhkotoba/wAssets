package com.wAssets.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.wAssets.common.Constant;
import com.wAssets.common.ResponseModel;

import reactor.core.publisher.Mono;

@Component
public class AccountHandler {
	
	@Autowired
	private AccountService accountService;

	public Mono<ServerResponse> saveAccount(ServerRequest request){
		
		ResponseModel<AccountModel> result = new ResponseModel<AccountModel>();		
		
		return request.bodyToMono(AccountModel.class)
			//계좌 밸류데이션체크
			.flatMap(accountService::vaildAccount)
			//계좌 저장
			.flatMap(accountService::insertAccount)
			.flatMap(map-> {				
				
				result.setData(map);
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				
				//응답
				return ServerResponse.ok()						
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(result));
			}).onErrorResume(error -> {
				result.setResultCode(error.getMessage());
				
				return ServerResponse.ok()						
					.contentType(MediaType.APPLICATION_JSON)
					.body(BodyInserters.fromValue(result));
			});
			
		
		
		
		
		//return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
		//		.body(BodyInserters.fromValue("{\"TEXT\":\"RESULT TEST\"}"));		
	}
}
