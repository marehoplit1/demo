package com.example.controller.responses;

import com.example.model.Route;
import lombok.Data;

import java.util.List;

/**
 * @author mgudelj
 */
@Data
public class CityDistanceResponse {

    private List<String> routes;

    private double totalPrice ;
}
