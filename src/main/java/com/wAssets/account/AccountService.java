package com.wAssets.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.wAssets.account.model.AccountModel;
import com.wAssets.common.AssetsException;
import com.wAssets.common.Constant;
import com.wAssets.common.Utils;
import com.wAssets.common.model.ApplyModel;
import com.wAssets.common.model.SessionModel;

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
			//행 상태(insert, update)
			if(Constant.GRID_STATE_INSERT.equals(model.get_state()) || Constant.GRID_STATE_UPDATE.equals(model.get_state())) {
				
				//빈값 체크			
				if(ObjectUtils.isEmpty(model.getAcctTgtCd()) 		||
						ObjectUtils.isEmpty(model.getAcctDivCd()) 	|| 
						ObjectUtils.isEmpty(model.getAcctNum()) 	|| 
						ObjectUtils.isEmpty(model.getCratDt()) 		|| 
						ObjectUtils.isEmpty(model.getEpyDtUseYn()) 	||					
						ObjectUtils.isEmpty(model.getUseYn())
				) {	
					return Mono.error(new RuntimeException(Constant.CODE_ESSENTIAL_DATA_EMPTY));
				}
				
				//생성일 날짜형식 체크(YYYYMMDD)
				if(Utils.isNotDate(model.getCratDt(), Constant.YYYYMMDD)) {
					return Mono.error(new RuntimeException(Constant.CODE_DATE_ERROR));
					
				//만기일 날짜형식 체크(YYYYMMDD)
				}else if(Constant.Y.equals(model.getEpyDtUseYn())) {
					if(Utils.isNotDate(model.getEpyDt(), Constant.YYYYMMDD)) {
						return Mono.error(new RuntimeException(Constant.CODE_DATE_ERROR));
					}
				}
				
				//정렬순서 값이 비어있을경우 0으로 세팅
				if(ObjectUtils.isEmpty(model.getAcctOdr())) {
					model.setAcctOdr(0);
				}
				//체크완료
				return Mono.just(model);
				
			//행 상태(delete)
			}else if(Constant.GRID_STATE_REMOVE.equals(model.get_state())) {
				
				//가계부에 사용되는 계좌인지 체크
				//if() {
				//	new RuntimeException(Constant.)
				//}
				
				//체크완료
				return Mono.just(model);
				
			//그 외의 행 상태
			}else {
				return Mono.error(new RuntimeException(Constant.CODE_UNKNOWN_ERROR));
			}
		}catch(Exception e) {
			return Mono.error(new RuntimeException(Constant.CODE_UNKNOWN_ERROR));
		}
	}
	
	/**
	 * 계좌등록 밸류데이션
	 * @param map
	 * @return
	 */
	public String vaildAccount2(AccountModel model){
		
		//행 상태(insert, update)
		if(Constant.GRID_STATE_INSERT.equals(model.get_state()) || Constant.GRID_STATE_UPDATE.equals(model.get_state())) {
			
			//빈값 체크			
			if(ObjectUtils.isEmpty(model.getAcctTgtCd()) 		||
					ObjectUtils.isEmpty(model.getAcctDivCd()) 	|| 
					ObjectUtils.isEmpty(model.getAcctNum()) 	|| 
					ObjectUtils.isEmpty(model.getCratDt()) 		|| 
					ObjectUtils.isEmpty(model.getEpyDtUseYn()) 	||					
					ObjectUtils.isEmpty(model.getUseYn())
			) {	
				return Constant.CODE_ESSENTIAL_DATA_EMPTY;
			}
			
			//생성일 날짜형식 체크(YYYYMMDD)
			if(Utils.isNotDate(model.getCratDt(), Constant.YYYYMMDD)) {
				return Constant.CODE_DATE_ERROR;
				
			//만기일 날짜형식 체크(YYYYMMDD)
			}else if(Constant.Y.equals(model.getEpyDtUseYn())) {
				if(Utils.isNotDate(model.getEpyDt(), Constant.YYYYMMDD)) {
					return Constant.CODE_DATE_ERROR;
				}
			}
			
			//정렬순서 값이 비어있을경우 0으로 세팅
			if(ObjectUtils.isEmpty(model.getAcctOdr())) {
				model.setAcctOdr(0);
			}
			//체크완료
			return Constant.CODE_SUCCESS;
			
		//행 상태(delete)
		}else if(Constant.GRID_STATE_REMOVE.equals(model.get_state())) {
			
			//가계부에 사용되는 계좌인지 체크
			//if() {
			//	new RuntimeException(Constant.)
			//}
			
			//체크완료
			return Constant.CODE_SUCCESS;
			
		//그 외의 행 상태
		}else {
			return Constant.CODE_UNKNOWN_ERROR;
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
	@Transactional
	public Mono<ApplyModel> applyAccount(List<AccountModel> acctList, int userSeq){
		
		//적용회수 반환 모델
		ApplyModel apply = new ApplyModel();
		
		//적용할 계좌목록
		return Flux.fromStream(acctList.stream())
			.flatMap(account -> {
				account.setUserSeq(userSeq);
				
				String vaild = this.vaildAccount2(account);
				if(Constant.CODE_SUCCESS.equals(vaild) == false) {
					return Mono.error(new AssetsException(vaild));
				}
				
				//저장, 수정, 삭제 분기
				switch(account.get_state()) {
				case Constant.GRID_STATE_INSERT :
					return this.insertAccount(account).flatMap(count -> {
						apply.insertPlus(); return Mono.empty();
					});
				case Constant.GRID_STATE_UPDATE :
					return this.updateAccount(account).flatMap(count -> {
						apply.updatePlus(); return Mono.empty();
					});
				case Constant.GRID_STATE_REMOVE : 
					return this.deleteAccount(account).flatMap(count -> {
						apply.deletePlus(); return Mono.empty();
					});
				default : 
					return Mono.empty();
				}
			}).collectList().flatMap(m -> Mono.just(apply));
	}
}
