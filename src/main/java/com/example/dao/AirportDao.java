package com.example.dao;

import com.example.model.Airport;
import com.example.model.City;
import com.example.model.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mgudelj
 */
@Repository
public interface AirportDao extends CrudRepository<Airport, Long> {
    Airport findAirportByAirportID(int airportId);
}