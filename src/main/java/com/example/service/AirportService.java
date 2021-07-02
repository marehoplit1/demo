package com.example.service;

import com.example.model.Airport;

import java.io.InputStream;
import java.util.HashMap;

/**
 * @author mgudelj
 */
public interface AirportService {

    void save(Airport airport);

    void saveAll(HashMap<String, Airport> airports);

    public void importAirports(InputStream f);
}
