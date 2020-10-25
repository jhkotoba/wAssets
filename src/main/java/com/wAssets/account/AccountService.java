package com.wAssets.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.wAssets.common.Constant;
import com.wAssets.common.SessionModel;
import com.wAssets.common.Utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Component
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * 계좌등록 밸류데이션
	 * @param map
	 * @return
	 */
	public Mono<AccountModel> vaildAccount(AccountModel model){
		try {
			//빈값 체크			
			if(StringUtils.isEmpty(model.getAcctTgtCd()) 		||
					StringUtils.isEmpty(model.getAcctDivCd()) 	|| 
					StringUtils.isEmpty(model.getAcctNum()) 	|| 
					StringUtils.isEmpty(model.getCratDt()) 		|| 
					StringUtils.isEmpty(model.getEpyDtUseYn()) 	|| 
					StringUtils.isEmpty(model.getEpyDt()) 		|| 
					StringUtils.isEmpty(model.getUseYn())
			) Mono.error(new RuntimeException(Constant.CODE_VALIDATION_ACCOUNT));
			
			//생성일 날짜형식 체크(YYYYMMDD)
			if(Utils.isNotDate(model.getCratDt(), Constant.YYYYMMDD)) {
				Mono.error(new RuntimeException(Constant.CODE_VALIDATION_ACCOUNT));
				
			//만기일 날짜형식 체크(YYYYMMDD)
			}else if(Constant.Y.equals(model.getEpyDtUseYn())) {
				if(Utils.isNotDate(model.getEpyDt(), Constant.YYYYMMDD)) {
					Mono.error(new RuntimeException(Constant.CODE_VALIDATION_ACCOUNT));
				}
			}			
			
			//체크완료
			return Mono.create(sink -> sink.success(model));
			//return Mono.defer(() -> Mono.just(model));
		}catch(Exception e) {
			return Mono.error(new RuntimeException(Constant.CODE_UNKNOWN_ERROR));
		}
	}
	
	/**
	 * 계좌저장
	 * @param map
	 * @return
	 */
	public Mono<Integer> insertAccount(Tuple2<AccountModel, SessionModel> tuple){
		try {
			AccountModel model = tuple.getT1();
			model.setUserSeq(tuple.getT2().getUserSeq());
			return accountRepository.insertAccount(model);
		}catch (Exception e) {			
			return Mono.error(new RuntimeException(Constant.CODE_REPOSITORY_ERROR, e));
		}
	}
	
	/**
	 * 계좌목록 조회
	 * @param request
	 * @param session
	 * @return
	 */
	public Flux<AccountModel> selectAccountList(ServerRequest request, SessionModel session){
		try {
			return accountRepository.selectAccountList(request.queryParams(), session.getUserSeq());
		}catch (Exception e) {
			return Flux.error(new RuntimeException(Constant.CODE_REPOSITORY_ERROR, e));
		}
	}
}
