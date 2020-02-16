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

@Builder
@Getter
@ToString
public class ReviewStatisticsCalculator implements ReviewCalculator {

    private Map<LocalDate, List<Review>> reviewHistory;

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
