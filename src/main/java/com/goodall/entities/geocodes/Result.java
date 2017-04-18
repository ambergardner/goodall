package com.goodall.entities.geocodes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonIgnoreProperties(ignoreUnknown = true)

    Geometry geometry;

//    @JsonProperty("formatted-address")
//    private String formattedAddress;

//    public String getFormattedAddress() {
//        return formattedAddress;
//    }
//
//    public void setFormattedAddress(String formattedAddress) {
//        this.formattedAddress = formattedAddress;
//    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
