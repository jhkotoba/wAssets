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
		public RouterFunction<ServerResponse> apiRouter(AccountHandler accountHandler){
			return RouterFunctions			
				//계좌등록
				.route(RequestPredicates.POST("/api/assets/saveAccount")
					.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), accountHandler::saveAccount);
		}
}
