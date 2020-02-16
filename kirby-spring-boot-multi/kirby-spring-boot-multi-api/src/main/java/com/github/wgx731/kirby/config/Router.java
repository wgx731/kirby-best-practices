package com.github.wgx731.kirby.config;

import com.github.wgx731.kirby.handler.CaseInsensitiveRequestPredicate;
import com.github.wgx731.kirby.handler.ReviewHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Configuration For Routing Functions
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class Router {

    @NonNull
    private ReviewHandler reviewHandler;

    /**
     * Setup all ending points with handlers
     * @return application router function
     */
    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route(
            i(POST("/reviews/{date}")), reviewHandler::addReview
        ).andRoute(
            i(GET("/reviews/{date}/{name}")), reviewHandler::getDailyReport
        );
    }

    static RequestPredicate i(RequestPredicate target) {
        return new CaseInsensitiveRequestPredicate(target);
    }

}
