package com.github.wgx731.kirby.handler;

import com.github.wgx731.kirby.config.Router;
import com.github.wgx731.kirby.model.Review;
import com.github.wgx731.kirby.service.ReviewService;
import com.github.wgx731.kirby.service.impl.InMemoryReviewServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

@ExtendWith({
    SpringExtension.class,
    RestDocumentationExtension.class
})
class ReviewHandlerTest {

    private ReviewHandler testCase;

    private InMemoryReviewServiceImpl reviewService;

    private WebTestClient client;

    private Router router;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDoc) {
        reviewService = new InMemoryReviewServiceImpl();
        reviewService.setDailyEmptyTemplate(new ClassPathResource("template/daily_empty.txt"));
        reviewService.setDailyTemplate(new ClassPathResource("template/daily.txt"));
        testCase = new ReviewHandler(reviewService);
        router = new Router(testCase);
        client = WebTestClient
            .bindToRouterFunction(router.routes())
            .configureClient()
            .filter(documentationConfiguration(restDoc))
            .build();
        client.post()
            .uri("/reviews/2020-01-01")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(Review
                .builder()
                .name("kirby")
                .score(Double.valueOf(9.8))
                .build()), Review.class)
            .exchange();
    }

    @AfterEach
    void tearDown() {
        client = null;
        router = null;
        testCase = null;
        reviewService = null;
    }

    @Test
    void addReview() {
        client.post()
            .uri("/reviews/2020-01-02")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(Review
                .builder()
                .name("kirby")
                .score(Double.valueOf(9.8))
                .build()), Review.class)
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody()
            .consumeWith((document("add_review")));
    }

    @Test
    void getDailyReport() {
        client.get()
            .uri("/reviews/2020-01-01/kirby")
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody()
            .consumeWith(document("get_daily_report"));
    }

}