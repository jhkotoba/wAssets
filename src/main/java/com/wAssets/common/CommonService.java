package com.wAssets.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Mono;

@Component
public class CommonService {
	
	@Autowired
	private Gateway gateway;
	
	/**
	 * 세션정보 조회
	 * @param request
	 * @return
	 */
	public Mono<SessionModel> getSession(ServerRequest request){		
		return WebClient
	        .create()
	        .post()
	        .uri(gateway.getUrl() + "/api/member/getInnerSession")
	        .cookies(cookies -> {
	        	for (String key : request.cookies().keySet()) {	        		
					cookies.add(key, request.cookies().get(key).get(0).getValue());
				}
	        })
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(new SessionModel())
	        .accept(MediaType.APPLICATION_JSON)
	        .retrieve()
	        .bodyToMono(SessionModel.class);
	}
}
