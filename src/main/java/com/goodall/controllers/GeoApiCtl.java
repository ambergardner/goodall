package com.goodall.controllers;

import com.goodall.entities.geocodes.Geocode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GeoApiCtl {
    String coordinates;

    private final static String APIKEY = "AIzaSyCTNiSGsl475_XqhD6mDf0oa-RiHi68WuQ";
    // working example url request with street only address
    // https://maps.googleapis.com/maps/api/geocode/json?address=2221w21stst&AIzaSyCTNiSGsl475_XqhD6mDf0oa-RiHi68WuQ
    // build a string request for latitude/longitude lookup
    // https://maps.googleapis.com/maps/api/geocode/outputFormat?parameters

    public String makeGeocodeRequest(String address){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://maps.googleapis.com/maps/api/geocode/" + "json?address=" + address + "&" + APIKEY;

        RestTemplate template = new RestTemplate();
        ResponseEntity<Geocode> geocode = template.exchange(url, HttpMethod.GET, entity, Geocode.class);
        String lat = geocode.getBody().getLat();
        String lng = geocode.getBody().getLng();
        String coordinates = lat + ", " + lng;

        return coordinates;
    }
}
