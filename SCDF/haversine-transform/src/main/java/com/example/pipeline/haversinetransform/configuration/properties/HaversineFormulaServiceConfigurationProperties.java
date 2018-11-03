package com.example.pipeline.haversinetransform.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "haversine-properties")
public class HaversineFormulaServiceConfigurationProperties {
    private Double lat;
    private Double lon;
    private int radiusInKilometers;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public int getRadiusInKilometers() {
        return radiusInKilometers;
    }

    public void setRadiusInKilometers(int radiusInKilometers) {
        this.radiusInKilometers = radiusInKilometers;
    }
}
