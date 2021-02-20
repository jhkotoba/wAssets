package com.wAssets.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AccountRouter {

		@Bean
		public RouterFunction<ServerResponse> accountRoute(AccountHandler accountHandler){
			return RouterFunctions
				//계좌등록
				.route(RequestPredicates.POST("/api/assets/saveAccount")
					.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), accountHandler::saveAccount)
				//계좌목록조회
				.andRoute(RequestPredicates.GET("/api/assets/getAccountList")
					.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), accountHandler::getAccountList)
				//계좌정보조회
				.andRoute(RequestPredicates.GET("/api/assets/getAccount")
					.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), accountHandler::getAccount)		
				//계좌정보변경 저장(등록, 수정, 삭제)
				.andRoute(RequestPredicates.POST("/api/assets/applyAccount")
					.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), accountHandler::applyAccount);
		}
}
