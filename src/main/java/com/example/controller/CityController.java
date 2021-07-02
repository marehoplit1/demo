package com.example.controller;

import com.example.controller.requests.CityRequest;
import com.example.controller.requests.CommentRequest;
import com.example.controller.responses.CityDistanceResponse;
import com.example.controller.responses.CityResponse;
import com.example.model.Airport;
import com.example.model.City;
import com.example.model.CityReview;
import com.example.model.User;
import com.example.service.CityService;
import com.example.service.ReviewService;
import com.example.service.UserService;
import com.example.util.ResultDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mgudelj
 */
@Log4j2
@Validated
@RestController
@RequestMapping("/api/rest/v1")
public class CityController {

    CityService cityService;
    UserService userService;
    ReviewService reviewService;

    public CityController(CityService cityService, UserService userService,ReviewService reviewService) {
        this.cityService = cityService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping(path = "/cities/distance", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Valid
    public CityDistanceResponse getDistance
            (@RequestParam(value = "from", required = true) String from,
             @RequestParam(value = "to", required = true) String to) {

        City sourceCity = cityService.findCityByName(from);
        City targetCity = cityService.findCityByName(to);

        List<Airport> sourceAirports = sourceCity.getAirports();
        List<Airport> targetAirports = targetCity.getAirports();

        List<ResultDto> resultDtoList = new ArrayList<>();
        for (Airport source : sourceAirports)
            for (Airport target : targetAirports) {
                ResultDto resultList = cityService.getShortestPath(source, target);
                resultDtoList.add(resultList);
            }
        ResultDto min = resultDtoList.stream().min(Comparator.comparing(ResultDto::getPrice))
                .orElseThrow(NoSuchElementException::new);

        CityDistanceResponse cityDistanceResponse = new CityDistanceResponse();
        cityDistanceResponse.setTotalPrice(min.getPrice());
        cityDistanceResponse.setRoutes(min.getRoute().stream().map(vert -> vert.getName()).collect(Collectors.toList()));
        return cityDistanceResponse;
    }

    @PostMapping(value = "/cities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCity(@Valid @RequestBody CityRequest request, @AuthenticationPrincipal UserDetails userDetails) {

        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        if( authorities.contains("ROLE_ADMIN")){
            log.debug("Creating city: " + request);

            City city = City.builder()
                    .name(request.getName())
                    .country(request.getCountry())
                    .description(request.getDescription())
                    .build();

            cityService.save(city);
        }
        else throw new AuthorizationServiceException("ERROR");
    }

    @GetMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Valid
    public List<CityResponse> getCities(@RequestParam(value = "comments", required = false) Integer commentsPerCity) {

        List<CityResponse> resultList = new LinkedList<>();

        List<City> cities = cityService.getAllCities();
        for (City city : cities) {
            CityResponse cityResponse = new CityResponse();
            cityResponse.setName(city.getName());
            cityResponse.setCountry(city.getCountry());
            cityResponse.setId(String.valueOf(city.getUuid()));
            if(commentsPerCity != null && commentsPerCity < city.getCityReviews().size()){
                List<CityReview> sublist = city.getCityReviews().subList(city.getCityReviews().size()-commentsPerCity,city.getCityReviews().size());
                cityResponse.setComments(sublist.stream().map(r->r.getContent()).collect(Collectors.toList()));
            }
            else cityResponse.setComments(city.getCityReviews().stream().map(r->r.getContent()).collect(Collectors.toList()));
            resultList.add(cityResponse);
        }

        return resultList;

    }

    @GetMapping(path = "/cities/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Valid
    public List<CityResponse> getCities( @RequestParam("filter-name") String name)  {
        List<CityResponse> resultList = new LinkedList<>();

        List<City> cities = cityService.getCitiesByName(name);
        for (City city : cities) {
            CityResponse cityResponse = new CityResponse();
            cityResponse.setName(city.getName());
            cityResponse.setCountry(city.getCountry());
            cityResponse.setId(String.valueOf(city.getUuid()));
            cityResponse.setComments(city.getCityReviews().stream().map(r->r.getContent()).collect(Collectors.toList()));
            resultList.add(cityResponse);
        }

        return resultList;

    }

    @PostMapping(value = "/cities/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addReview(@Valid @RequestBody CommentRequest request
            , @PathVariable String id
            , @AuthenticationPrincipal UserDetails userDetails) {

        City city = cityService.findCityById(id);

        CityReview cityReview = new CityReview();
        cityReview.setContent(request.getComment());
        cityReview.setCity(city);

        String username = userDetails.getUsername();
        User user = userService.getByUsername(username);
        cityReview.setUser(user);

        reviewService.save(cityReview);
    }

    @DeleteMapping(value = "/cities/{id}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCityReview(@PathVariable("id") String id,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable("commentId") String commentId
    )
    {
        City city = cityService.findCityById(id);
        List<CityReview> comments = city.getCityReviews();
        CityReview cityReview = comments.stream()
                .filter(comment -> comment.getUuid().toString().equals(commentId))
                .findAny()
                .orElse(null);

        reviewService.delete(cityReview) ;

    }

    @PatchMapping(path = "/cities/{id}/comments/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateCityReview(@PathVariable("id") String id,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 @PathVariable("commentId") String commentId,
                                 @Valid @RequestBody CommentRequest request
    )
    {
        City city = cityService.findCityById(id);
        List<CityReview> comments = city.getCityReviews();
        CityReview cityReview = comments.stream()
                .filter(comment -> comment.getUuid().equals(commentId))
                .findAny()
                .orElse(null);
        cityReview.setContent(request.getComment());

        reviewService.save(cityReview);
    }
}
