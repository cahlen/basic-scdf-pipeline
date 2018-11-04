package com.example.restapi.pipelinerestapi.dao;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Starbucks starbucks = (Starbucks) o;
        return Objects.equals(latitude, starbucks.latitude) &&
                Objects.equals(longitude, starbucks.longitude) &&
                Objects.equals(storeName, starbucks.storeName) &&
                Objects.equals(address, starbucks.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, storeName, address);
    }
}
