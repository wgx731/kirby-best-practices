package com.github.wgx731.kirby.service;

import com.github.wgx731.kirby.model.Review;

import java.time.LocalDate;
import java.util.List;

public interface ReviewService {

    List<Review> addReview(LocalDate date, Review review);

    String getDailyReport(LocalDate date, String name);

}
