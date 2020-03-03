package com.github.wgx731.kirby.helper;

import com.github.wgx731.kirby.model.Review;

import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Review Summary Calculator
 */
public interface ReviewCalculator {

    /**
     * Get summary of a list of reviews
     * @param reviews list of reviews
     * @return statistics of the given reviews
     */
    default DoubleSummaryStatistics getSummary(List<Review> reviews) {
        return reviews.stream()
            .mapToDouble(Review::getScore)
            .summaryStatistics();
    }

}
