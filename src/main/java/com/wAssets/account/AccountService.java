package com.wAssets.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.wAssets.common.Constant;
import com.wAssets.common.Utils;

import reactor.core.publisher.Mono;

@Component
public class AccountService {
	
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
			) Mono.error(new RuntimeException(Constant.RESULT_CODE_VALIDATION_ACCOUNT));
			
			//날짜형식 체크(YYYYMMDD)
			if(Utils.isNotDate(model.getCratDt(), Constant.YYYYMMDD) 		||
					Utils.isNotDate(model.getEpyDt(), Constant.YYYYMMDD)					
			) Mono.error(new RuntimeException(Constant.RESULT_CODE_VALIDATION_ACCOUNT));			
			
			return Mono.defer(() -> Mono.just(model));
		}catch(Exception e) {
			return Mono.error(new RuntimeException(Constant.RESULT_CODE_UNKNOWN_ERROR));
		}
	}
	
	/**
	 * 계좌저장
	 * @param map
	 * @return
	 */
	public Mono<AccountModel> insertAccount(AccountModel model){
		return Mono.just(model);
	}

}
