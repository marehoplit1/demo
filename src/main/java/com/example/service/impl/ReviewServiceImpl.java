package com.example.service.impl;

import com.example.dao.ReviewDao;
import com.example.model.CityReview;
import com.example.service.ReviewService;
import org.springframework.stereotype.Service;

/**
 * @author mgudelj
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    ReviewDao reviewDao;

    @Override
    public void save(CityReview cityReview) {
        reviewDao.save(cityReview);
    }

    @Override
    public void delete(CityReview cityReview) {
        reviewDao.delete(cityReview);
    }
}
