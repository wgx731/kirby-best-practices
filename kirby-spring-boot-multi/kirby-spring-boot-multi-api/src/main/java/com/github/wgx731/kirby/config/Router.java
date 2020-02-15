package com.github.wgx731.kirby.config;

import com.github.wgx731.kirby.handler.CaseInsensitiveRequestPredicate;
import com.github.wgx731.kirby.handler.ReviewHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Router {

    @Bean
    RouterFunction<ServerResponse> routes(ReviewHandler handler) {
        return route(i(POST("/reviews/{date}")), handler::addReview)
            .andRoute(i(GET("/reviews/{date}/{name}")), handler::getDailyReport);
    }

    static RequestPredicate i(RequestPredicate target) {
        return new CaseInsensitiveRequestPredicate(target);
    }

}
