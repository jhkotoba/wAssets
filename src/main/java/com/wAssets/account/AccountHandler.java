package com.wAssets.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.wAssets.common.CommonService;
import com.wAssets.common.Constant;
import com.wAssets.common.ResponseModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AccountService accountService;

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
	
	public Mono<ServerResponse> getAccountList(ServerRequest request){
		System.out.println("HANDLER getAccountList");
		
//		Mono<?> f = accountService.selectAccountList()
//		.doOnNext(onNext -> System.out.println("onNext:"+onNext))
//		.flatMap(ff -> {
//			return Mono.just(ff);
//		});
		//.subscribe();
		
//		Flux<?> flux = accountService.selectAccountList();
//		Mono<List<?>> m = flux.toIterable().toList();
		
		Flux<Map<String, Object>> flux = accountService.selectAccountList();
		Mono<List<Map<String, Object>>> m =  flux.collectList();
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(m));
		
		
		
		//return request.bodyToMono(AccountModel.class)
			//세션조회
			//.zipWith(commonService.getSession(request))
//			.doOnNext(onNext -> System.out.println(onNext))
//			//계좌목록 조회
//			.flatMap(tuple -> {
//				
//				System.out.println("tuple:" +tuple);
//				return ServerResponse.ok()
//						.contentType(MediaType.APPLICATION_JSON)
//						.body(BodyInserters.fromValue((accountService.selectAccountList(tuple))));
//				
//				
//				
//			});
		
//		commonService.getSession(request)
//			.flatMap(sm -> {
//				AccountModel am = new AccountModel();
//				return Mono.just(am.setUserSeq(sm.getUserSeq()));
//			});
		//Flux<?> f = commonService.getSession(request).flatMap(accountService::selectAccountList);
			//.flatMap(sm -> {
			//	AccountModel am = new AccountModel();
			//	am.setUserSeq(sm.getUserSeq());
			//	return Mono.just(am);
			//	///return Mono.defer(() -> Mono.just(am));
			//}).flatMap(accountService::selectAccountList);
		
		//return request.bodyToMono(AccountModel.class)
		
//		Flux.just(commonService.getSession(request))
//				.flatMap(accountService::selectAccountList);
//		
//		return ServerResponse.ok()						
//				.contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromValue(f));
			
		
		//return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Flux.just("1","2","3"), String.class);
	}
}
