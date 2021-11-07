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
	
	/**
	 * 장부저장
	 * @param ledger
	 * @return
	 */
	@Transactional
	public Mono<Integer> registLedger(LedgerModel ledger) {
		return ledgerRepository.insertLedger(ledger);
	}
}
