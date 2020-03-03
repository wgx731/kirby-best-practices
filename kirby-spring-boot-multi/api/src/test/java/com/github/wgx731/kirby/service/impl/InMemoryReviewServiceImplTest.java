package com.github.wgx731.kirby.service.impl;

import com.github.wgx731.kirby.model.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class InMemoryReviewServiceImplTest {

    private InMemoryReviewServiceImpl testCase;

    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testCase = new InMemoryReviewServiceImpl();
        testCase.setDailyEmptyTemplate(new ClassPathResource("template/daily_empty.txt"));
        testCase.setDailyTemplate(new ClassPathResource("template/daily.txt"));
        testDate = LocalDate.of(2020, 1, 1);
    }

    @AfterEach
    void tearDown() {
        testDate = null;
        testCase = null;
    }

    @Test
    void getStringFromResource() {
        String text = testCase.getStringFromResource(new ClassPathResource("template/daily_empty.txt"));
        assertThat(text).contains("DAILY REVIEW REPORT");
        assertThat(text).contains("No review found");
        Throwable thrown = catchThrowable(() -> {
            testCase.getStringFromResource(new ClassPathResource("template/not_there.txt"));
        });
        assertThat(thrown)
            .isInstanceOf(UncheckedIOException.class)
            .hasMessageContaining("not_there.txt");
    }

    @Test
    void addReview() {
        List<Review> reviews = testCase.addReview(testDate, Review
            .builder()
            .name("test")
            .score(Double.valueOf(3.5))
            .build());
        assertThat(reviews.size()).isEqualTo(1);
        assertThat(reviews.get(0).getName()).isEqualTo("test");
        reviews = testCase.addReview(testDate, Review
            .builder()
            .name("test")
            .score(Double.valueOf(4.0))
            .build());
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(1).getName()).isEqualTo("test");
        assertThat(reviews.get(1).getScore()).isEqualTo(Double.valueOf(4.0));
        reviews = testCase.addReview(testDate, Review
            .builder()
            .name("another")
            .score(Double.valueOf(4.0))
            .build());
        assertThat(reviews.size()).isEqualTo(3);
        assertThat(reviews.get(2).getName()).isEqualTo("another");
    }

    @Test
    void getDailyReport() {
        testCase.addReview(testDate, Review
            .builder()
            .name("test1")
            .score(Double.valueOf(3.0))
            .build());
        testCase.addReview(testDate, Review
            .builder()
            .name("test1")
            .score(Double.valueOf(5.0))
            .build());
        String test1Report = testCase.getDailyReport(testDate, "test1");
        assertThat(test1Report).contains("Total Review Count: 2");
        assertThat(test1Report).contains("Min Review Score: 3.0");
        assertThat(test1Report).contains("Max Review Score: 5.0");
        assertThat(test1Report).contains("Average Review Score: 4.0");
        String test2Report = testCase.getDailyReport(testDate, "test2");
        assertThat(test2Report).contains("Total Review Count: 0");
        assertThat(test2Report).contains("Min Review Score: Infinity");
        assertThat(test2Report).contains("Max Review Score: -Infinity");
        assertThat(test2Report).contains("Average Review Score: 0.0");
        String emptyReport = testCase.getDailyReport(testDate.plusDays(1), "test1");
        assertThat(emptyReport).contains("No review found");
    }
}