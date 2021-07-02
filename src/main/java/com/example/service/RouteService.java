package com.example.service;

import com.example.model.Route;

import java.util.LinkedList;
import java.util.List;

/**
 * @author mgudelj
 */
public interface RouteService {

    double getCheapestFlight(String source, String destination);

    void savaAll(LinkedList<Route> routes);

    List<Route> getAllRoutes();
}
