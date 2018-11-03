package com.example.pipeline.haversinetransform.service;

import com.example.pipeline.haversinetransform.configuration.properties.HaversineFormulaServiceConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HaversineFormulaService {

    private static final int EARTH_RADIUS = 6371;

    private HaversineFormulaServiceConfigurationProperties haversineConfig;

    @Autowired
    public HaversineFormulaService(HaversineFormulaServiceConfigurationProperties haversineConfig) {
        this.haversineConfig = haversineConfig;
    }

    public Boolean withinRadius(double lat, double lon) {
        return distance(lat, lon, haversineConfig.getLat(), haversineConfig.getLon()) < haversineConfig.getRadiusInKilometers();
    }

    private double distance(double startLat, double startLong,
                                  double endLat, double endLong) {

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
