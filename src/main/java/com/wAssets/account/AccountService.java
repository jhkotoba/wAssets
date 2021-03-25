package com.wAssets.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.wAssets.common.ApplyModel;
import com.wAssets.common.Constant;
import com.wAssets.common.SessionModel;
import com.wAssets.common.Utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
			if(ObjectUtils.isEmpty(model.getAcctTgtCd()) 		||
					ObjectUtils.isEmpty(model.getAcctDivCd()) 	|| 
					ObjectUtils.isEmpty(model.getAcctNum()) 	|| 
					ObjectUtils.isEmpty(model.getCratDt()) 		|| 
					ObjectUtils.isEmpty(model.getEpyDtUseYn()) 	||					
					ObjectUtils.isEmpty(model.getUseYn())
			) {	
				return Mono.error(new RuntimeException(Constant.CODE_VALIDATION_ACCOUNT));
			}
			
			//생성일 날짜형식 체크(YYYYMMDD)
			if(Utils.isNotDate(model.getCratDt(), Constant.YYYYMMDD)) {
				return Mono.error(new RuntimeException(Constant.CODE_VALIDATION_ACCOUNT));
				
			//만기일 날짜형식 체크(YYYYMMDD)
			}else if(Constant.Y.equals(model.getEpyDtUseYn())) {
				if(Utils.isNotDate(model.getEpyDt(), Constant.YYYYMMDD)) {
					return Mono.error(new RuntimeException(Constant.CODE_VALIDATION_ACCOUNT));
				}
			}
			
			//정렬순서 값이 비어있을경우 0으로 세팅
			if(ObjectUtils.isEmpty(model.getAcctOdr())) {
				model.setAcctOdr(0);
			}
			
			//체크완료
			return Mono.just(model);
		}catch(Exception e) {
			return Mono.error(new RuntimeException(Constant.CODE_UNKNOWN_ERROR));
		}
	}
	
	/**
	 * 계좌저장
	 * @param map
	 * @return
	 */	
	public Mono<Integer> insertAccount(AccountModel model){
		try {
			return accountRepository.insertAccount(model);
		}catch (Exception e) {
			return Mono.error(new RuntimeException(Constant.CODE_REPOSITORY_ERROR, e));
		}
	}
	
	/**
	 * 계좌수정
	 * @param model
	 * @return
	 */
	public Mono<Integer> updateAccount(AccountModel model){
		try {
			return accountRepository.updateAccount(model);
		}catch (Exception e) {
			return Mono.error(new RuntimeException(Constant.CODE_REPOSITORY_ERROR, e));
		}
	}
	
	/**
	 * 계좌삭제
	 * @param model
	 * @return
	 */
	public Mono<Integer> deleteAccount(AccountModel model){
		try {
			return accountRepository.deleteAccount(model);
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
			if(session.isLogin()) {
				return accountRepository.selectAccountList(request.queryParams(), session.getUserSeq())
					.switchIfEmpty(Mono.error(new RuntimeException(Constant.CODE_DATA_EMPTY)));
			}else {
				return Flux.error(new RuntimeException(Constant.CODE_NO_LOGIN));
			}
		}catch (Exception e) {
			return Flux.error(new RuntimeException(Constant.CODE_REPOSITORY_ERROR, e));
		}
	}
	
	/**
	 * 계좌정보 조회
	 * @param request
	 * @param session
	 * @return
	 */
	public Mono<AccountModel> selectAccount(ServerRequest request, SessionModel session){
		try {
			if(session.isLogin()) {
				return accountRepository.selectAccount(request.queryParams(), session.getUserSeq())
					.switchIfEmpty(Mono.error(new RuntimeException(Constant.CODE_DATA_EMPTY)));
			}else {
				return Mono.error(new RuntimeException(Constant.CODE_NO_LOGIN));
			}
		}catch (Exception e) {
			return Mono.error(new RuntimeException(Constant.CODE_REPOSITORY_ERROR, e));
		}
	}
	
	/**
	 * 계좌적용 (저장, 수정, 삭제)
	 * @param account
	 * @param userSeq
	 * @return
	 */
	public Mono<ApplyModel> applyAccount(AccountModel account, int userSeq){
		
		//사용자 번호 세팅
		account.setUserSeq(userSeq);
		//다사용 객체
		Mono<ApplyModel> result = null; 
		
		//저장, 수정, 삭제 분기
		switch(account.get_state()) {
		case Constant.GRID_STATE_INSERT :
			result = this.insertAccount(account).flatMap(count -> Mono.just(new ApplyModel(1, count)));
			
		case Constant.GRID_STATE_UPDATE :
			//result = Mono.error(new Exception("TEST"));
			//result = this.updateAccount(account).flatMap(count -> Mono.just(new ApplyModel(0, count)));
		case Constant.GRID_STATE_REMOVE : 
			//result = this.deleteAccount(account).flatMap(count -> Mono.just(new ApplyModel(-1, count)));
		}
		return result;
	}		
}
