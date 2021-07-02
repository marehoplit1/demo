package com.example.service;

import com.example.model.CityReview;

/**
 * @author mgudelj
 */
public interface ReviewService {
    void save(CityReview cityReview);

    void delete(CityReview cityReview);
}
