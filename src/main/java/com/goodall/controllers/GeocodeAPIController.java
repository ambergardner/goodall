package com.goodall.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class GeocodeAPIController {
    String coordinates;
    private final static String APIKEY = "AIzaSyCTNiSGsl475_XqhD6mDf0oa-RiHi68WuQ";
    // build a string request for latitude/longitude lookup
    // https://maps.googleapis.com/maps/api/geocode/outputFormat?parameters

    public String makeGeocodeRequest(String address){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://maps.googleapis.com/maps/api/geocode/" + "json?address=" + address + "&" + APIKEY;
        return coordinates;
    }
}
