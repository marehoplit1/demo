package com.example.service;

import com.example.model.Airport;
import com.example.model.City;
import com.example.util.ResultDto;

import java.util.HashSet;
import java.util.List;

/**
 * @author mgudelj
 */
public interface CityService {

    List<City> getAllCities();

    void save(City city);

    void savaAll(List<City> cities);

    City findCityByName(String from);

    City findCityById(String from);

    ResultDto getShortestPath(Airport source, Airport target);


    List<City> getCitiesByName(String name);
}
