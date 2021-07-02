package com.example.service.impl;

import com.example.dao.RouteDao;
import com.example.model.Route;
import com.example.service.RouteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * @author mgudelj
 */
@Service
public class RouteServiceImpl implements RouteService {

    private final RouteDao routeDao;

    public RouteServiceImpl(RouteDao routeDao) {
        this.routeDao = routeDao;
    }

    @Override
    public double getCheapestFlight(String source, String destination) {

        return 0;
    }

    @Override
    @Transactional
    public void savaAll(LinkedList<Route> routes) {
        routeDao.saveAll(routes);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> getAllRoutes() {
        return (List<Route>) routeDao.findAll();
    }
}
