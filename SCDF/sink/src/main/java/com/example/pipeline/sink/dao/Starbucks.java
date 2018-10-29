package com.example.pipeline.sink.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Starbucks {

    private Double latitude;
    private Double longitude;
    private String storeName;
    private String address;

    public Starbucks(Double latitude,
                     Double longitude,
                     String storeName,
                     String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeName = storeName;
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAddress() {
        return address;
    }

}
