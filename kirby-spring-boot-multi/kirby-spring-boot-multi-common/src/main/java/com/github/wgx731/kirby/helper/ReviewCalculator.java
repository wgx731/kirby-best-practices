package com.github.wgx731.kirby.helper;

import com.github.wgx731.kirby.model.Review;

import java.util.DoubleSummaryStatistics;
import java.util.List;

public interface ReviewCalculator {

    default DoubleSummaryStatistics getSummary(List<Review> reviews) {
        return reviews.stream()
            .mapToDouble(Review::getScore)
            .summaryStatistics();
    }

}
