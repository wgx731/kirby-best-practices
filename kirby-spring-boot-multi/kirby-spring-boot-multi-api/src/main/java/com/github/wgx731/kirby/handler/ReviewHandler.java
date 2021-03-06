package com.github.wgx731.kirby.handler;

import com.github.wgx731.kirby.model.Review;
import com.github.wgx731.kirby.service.ReviewService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

/**
 * Review Handler
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ReviewHandler {

    public static final ParameterizedTypeReference<List<Review>> LIST_REVIEWS_REF =
        new ParameterizedTypeReference<>() {
        };

    public static final String NAME = "name";
    public static final String DATE = "date";

    @NonNull
    private ReviewService reviewService;

    /**
     * Add review handler
     * @param request add review request
     * @return add review response
     */
    public Mono<ServerResponse> addReview(ServerRequest request) {
        LocalDate date = getDate(request);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromPublisher(request
                .bodyToMono(Review.class)
                .flatMap(review ->
                    Mono.just(reviewService.addReview(date, review))
                ), LIST_REVIEWS_REF));
    }

    /**
     * Daily report handler
     * @param request daily report request
     * @return daily report response
     */
    public Mono<ServerResponse> getDailyReport(ServerRequest request) {
        LocalDate date = getDate(request);
        String name = request.pathVariable(NAME);
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromValue(
                reviewService.getDailyReport(date, name)
            ));
    }

    LocalDate getDate(ServerRequest request) {
        return LocalDate.parse(
            request.pathVariable(DATE)
        );
    }

}
