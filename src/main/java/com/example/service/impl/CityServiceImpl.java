package com.example.service.impl;

import com.example.dao.CityDao;
import com.example.model.Airport;
import com.example.model.City;
import com.example.service.CityService;
import com.example.util.Dijkstra;
import com.example.util.ResultDto;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author mgudelj
 */
@Service
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;
    private final Dijkstra dijkstra;


    public CityServiceImpl(CityDao cityDao, Dijkstra dijkstra) {
        this.cityDao = cityDao;
        this.dijkstra = dijkstra;
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getAllCities() {
        return (List<City>) cityDao.findAll();
    }

    @Override
    @Transactional
    public void save(City city) {
        cityDao.save(city);
    }

    @Override
    @Transactional
    public void savaAll(List<City> cities) {
        cityDao.saveAll(cities);
    }

    @Override
    @Transactional(readOnly = true)
    public City findCityByName(String from) {
        return cityDao.findByName(from);
    }

    @Override
    @Transactional(readOnly = true)
    public City findCityById(String id) {
        City city = cityDao.findCityByUuid(UUID.fromString(id));
        Hibernate.initialize(city.getCityReviews());
        return city;
    }

    @Override
    public ResultDto getShortestPath(Airport source, Airport target) {

        try {
            Dijkstra d = new Dijkstra();
            d.readAirportsFromFile();
            return dijkstra.getShortestPathFromSourceTarget
                    (String.valueOf(source.getAirportID()),
                            String.valueOf(target.getAirportID()), d);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getCitiesByName(String name) {
        List<City> cities = cityDao.findByNameStartingWith(name);
        for(City city:cities)
            Hibernate.initialize(city.getCityReviews());
        return cities;
    }

}
