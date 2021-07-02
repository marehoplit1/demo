package com.example.dao;

import com.example.model.Airport;
import com.example.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author mgudelj
 */
@Repository
public interface CityDao extends CrudRepository<City, Long> {

    City findByName(String from);

    City findCityByUuid(UUID id) ;

    List<City> findByNameStartingWith(String name);
}

