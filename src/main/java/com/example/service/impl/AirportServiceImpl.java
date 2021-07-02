package com.example.service.impl;

import com.example.dao.AirportDao;
import com.example.model.Airport;
import com.example.service.AirportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author mgudelj
 */
@Service
public class AirportServiceImpl implements AirportService {

    AirportDao airportDao;

    public AirportServiceImpl(AirportDao airportDao) {
        this.airportDao = airportDao;
    }

    @Override
    @Transactional
    public void save(Airport airport) {
        airportDao.save(airport);
    }

    @Override
    @Transactional
    public void saveAll(HashMap<String, Airport> airports) {
        LinkedList<Airport> list =new LinkedList<Airport>(airports.values());
        airportDao.saveAll(list);
    }

    @Override
    public void importAirports(InputStream f) {

    }
}
