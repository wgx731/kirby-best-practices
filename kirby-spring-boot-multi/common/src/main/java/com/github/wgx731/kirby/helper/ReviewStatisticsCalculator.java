package com.github.wgx731.kirby.helper;

import com.github.wgx731.kirby.model.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Review Statistics Calculator
 */
@Builder
@Getter
@ToString
public class ReviewStatisticsCalculator implements ReviewCalculator {

    private Map<LocalDate, List<Review>> reviewHistory;

    /**
     * Get statistics of a given date and review from a map of reviews
     * @param date review date
     * @param name review name
     * @return statistics of reviews on given date with given name
     */
    public Optional<DoubleSummaryStatistics> getDailySummary(LocalDate date, String name) {
        if (!reviewHistory.containsKey(date)) {
            return Optional.empty();
        }
        return Optional.of(this.getSummary(
            reviewHistory
                .get(date)
                .stream()
                .filter(r -> r.getName().equals(name))
                .collect(Collectors.toList())
            )
        );
    }

}
