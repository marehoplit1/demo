package com.example.dao;

import com.example.model.CityReview;
import com.example.model.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mgudelj
 */
@Repository
public interface ReviewDao extends CrudRepository<CityReview, Long> {

}
