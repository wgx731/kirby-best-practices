package com.github.wgx731.kirby.service;

import com.github.wgx731.kirby.model.Review;

import java.time.LocalDate;
import java.util.List;

/**
 * Review Serivce Interface
 */
public interface ReviewService {

    /**
     * add a new review for a given date
     * @param date date of review
     * @param review review detail
     * @return list of reviews on the given date
     */
    List<Review> addReview(LocalDate date, Review review);

    /**
     * get a report of all reviews on given date with given name
     * @param date date of reviews
     * @param name review name
     * @return text of daily report for given review name
     */
    String getDailyReport(LocalDate date, String name);

}
