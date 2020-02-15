package com.github.wgx731.kirby.service.impl;

import com.github.wgx731.kirby.helper.ReviewStatisticsCalculator;
import com.github.wgx731.kirby.model.Review;
import com.github.wgx731.kirby.service.ReviewService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class InMemoryReviewServiceImpl implements ReviewService {

    private ReviewStatisticsCalculator calculator = ReviewStatisticsCalculator
        .builder()
        .reviewHistory(new ConcurrentHashMap<>())
        .build();

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-LLLL-dd");

    @Value("classpath:template/daily_empty.txt")
    @Setter
    private Resource dailyEmptyTemplate;

    @Value("classpath:template/daily.txt")
    @Setter
    private Resource dailyTemplate;

    String getStringFromResource(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public List<Review> addReview(LocalDate date, Review review) {
        Map<LocalDate, List<Review>> storage = calculator.getReviewHistory();
        List<Review> reviews = new ArrayList<>();
        if (storage.containsKey(date)) {
            reviews = storage.get(date);
        }
        reviews.add(review);
        storage.put(date, reviews);
        return Collections.unmodifiableList(reviews);
    }

    @Override
    public String getDailyReport(LocalDate date, String name) {
        Optional<DoubleSummaryStatistics> dailySummary = calculator.getDailySummary(date, name);
        if (dailySummary.isEmpty()) {
            return String.format(
                getStringFromResource(dailyEmptyTemplate),
                date.format(dateFormatter).toUpperCase(Locale.US),
                name.toUpperCase(Locale.US)
            );
        }
        DoubleSummaryStatistics statistics = dailySummary.get();
        return String.format(
            getStringFromResource(dailyTemplate),
            date.format(dateFormatter).toUpperCase(Locale.US),
            name.toUpperCase(Locale.US),
            statistics.getCount(),
            statistics.getMin(),
            statistics.getMax(),
            statistics.getAverage()
        );
    }

}
