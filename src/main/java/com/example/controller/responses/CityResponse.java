package com.example.controller.responses;

import lombok.Data;

import java.util.List;

/**
 * @author mgudelj
 */
@Data
public class CityResponse {

    private String id;

    private String name ;

    private String country  ;

    private List<String> comments ;

}
