package com.example.dao;

import com.example.model.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

/**
 * @author mgudelj
 */
@Repository
public interface RouteDao extends CrudRepository<Route, Long> {


}
