package com.github.wgx731.kirby.helper;

import com.github.wgx731.kirby.model.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewStatisticsCalculatorTest {


    private LocalDate day1 = LocalDate.of(2020, 01, 01);

    private ReviewStatisticsCalculator testCase;

    @BeforeEach
    void setUp() {
        List<Review> day1Reviews = new LinkedList<>();
        day1Reviews.add(Review
            .builder()
            .name("kriby-spring-boot-cli")
            .score(1.0)
            .build());
        day1Reviews.add(Review
            .builder()
            .name("kriby-spring-boot-cli")
            .score(3.0)
            .build());
        day1Reviews.add(Review
            .builder()
            .name("kriby-spring-boot-web")
            .score(3.0)
            .build());
        day1Reviews.add(Review
            .builder()
            .name("kriby-spring-boot-web")
            .score(1.0)
            .build());
        Map<LocalDate, List<Review>> map = new HashMap<>();
        map.put(day1, day1Reviews);
        testCase = ReviewStatisticsCalculator
            .builder()
            .reviewHistory(map)
            .build();
    }

    @AfterEach
    void tearDown() {
        testCase = null;
    }

    @Test
    void getDailyReport() {
        assertThat(testCase.getDailySummary(LocalDate.now(), "test")).isEmpty();
        assertThat(testCase.getDailySummary(day1, "test")).isNotEmpty();
        DoubleSummaryStatistics statistics = testCase.getDailySummary(day1, "kriby-spring-boot-cli").get();
        assertThat(statistics.getAverage()).isEqualTo(2.0);
        assertThat(statistics.getCount()).isEqualTo(2L);
        assertThat(statistics.getMax()).isEqualTo(3.0);
        assertThat(statistics.getMin()).isEqualTo(1.0);
        assertThat(statistics.getSum()).isEqualTo(4.0);
    }

}