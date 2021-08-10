package com.wAssets.ledger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wAssets.ledger.model.LedgerModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class LedgerService {

	@Autowired
	private LedgerRepository ledgerRepository;
	
	/**
	 * 장부정보 조회
	 * @param ledger
	 * @return
	 */
	public Mono<LedgerModel> getLedger(LedgerModel ledger){
		return ledgerRepository.selectLedger(ledger);
	}
	
	/**
	 * 장부목록 조회
	 * @param ledger
	 * @return
	 */
	public Flux<LedgerModel> getLedgerList(LedgerModel ledger){
		return ledgerRepository.selectLedgerList(ledger);
	}
	
	@Transactional
	public Mono<Integer> appayBasicLedger(LedgerModel ledger) {
		//저장
		if(ledger.getLedIdx() == null) {
			return ledgerRepository.insertBasicLedger(ledger);
		//수정
		}else {
			return ledgerRepository.updateBasicLedger(ledger);
		}
	}
}
