package com.example.controller.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author mgudelj
 */
@Data
public class CityRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String country;

    @NotEmpty
    private String description;
}
