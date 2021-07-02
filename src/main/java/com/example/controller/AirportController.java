package com.example.controller;

import com.example.model.Airport;
import com.example.model.City;
import com.example.model.Route;
import com.example.service.AirportService;
import com.example.service.RouteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author mgudelj
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/api/rest/v1")
public class AirportController {

    HashMap<String,Airport> airports;
    HashMap<String,City> cities ;
    private final AirportService airportService;
    private HashSet<Route> routes;
    private final RouteService routeService;

    public AirportController(AirportService airportService, RouteService routeService) {
        this.airportService = airportService;
        this.routeService = routeService;
    }

    @PostMapping(path = "/airports/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void importAirports(@RequestPart("file") @NotNull MultipartFile file,@AuthenticationPrincipal UserDetails userDetails) {

        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        if( authorities.contains("ROLE_ADMIN")){

            airports = new HashMap<>();
            cities = new HashMap<String,City>();

            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                        .lines()
                        .forEach(this::handleAirport);

                var fileName = file.getOriginalFilename();
                var is = file.getInputStream();

                String path = "src/main/resources/";
                Files.copy(is, Paths.get(path + fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            airportService.saveAll(airports);
        }
        else throw new AuthorizationServiceException("ERROR");
    }

    private void handleAirport(String s) {
        System.out.println(s);
        String [] data = s.split(",");
        Airport airport = new Airport();
        airport.setName(data[1]);
        airport.setAirportID(Integer.parseInt(data[0]));

        City city = new City(data[2].substring(1,data[2].length()-1),data[3].substring(1,data[3].length()-1));
        city.setDescription("text");

        if(cities.get(city.getName()) == null) {
            cities.put(city.getName(),city);
            airport.setCity(city);
        } else {
            airport.setCity(cities.get(city.getName()));
        }

        airport.setCountry(data[3].substring(1,data[3].length()-1));
        log.info("added airport {} ", airport);
        airports.put(data[0],airport);
    }

    @PostMapping(path = "/routes/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void importRoutes(@RequestPart("file") @NotNull MultipartFile file,@AuthenticationPrincipal UserDetails userDetails) {

        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        if( authorities.contains("ROLE_ADMIN")){

            InputStream inputStream = null;
            routes = new HashSet();
            try {
                inputStream = file.getInputStream();
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                        .lines()
                        .forEach(this::handleRoute);

                var fileName = file.getOriginalFilename();
                var is = file.getInputStream();

                String path = "src/main/resources/";
                Files.copy(is, Paths.get(path + fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LinkedList<Route> routeList = new LinkedList();
            for (Route r : routes) {
                routeList.add(r);
            }
            routeService.savaAll(routeList);
        }
        else throw new AuthorizationServiceException("ERROR");


    }

    private void handleRoute(String s) {
        String[] data = s.split(",");
        try {
            Route route = new Route();
            route.setUuid(UUID.randomUUID());
            route.setSourceAirport(airports.get(data[3]));
            route.setTargetAirport(airports.get(data[5]));
            route.setPrice(Double.parseDouble(data[9]));
            log.info("added route {} ", route);
            if (route.getSourceAirport() != null && route.getTargetAirport() != null)
                routes.add(route);
        } catch (NumberFormatException e) {
            log.error("ERROR " + data[3], data[5], data[9]);
        }
    }

}
