package com.example.pipeline.haversinetransform.service;

import com.example.pipeline.haversinetransform.configuration.properties.HaversineFormulaServiceConfigurationProperties;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HaversineFormulaServiceTest {
    private HaversineFormulaService subject;

    @Before
    public void setUp() {
        HaversineFormulaServiceConfigurationProperties haversineFormulaServiceConfigurationProperties = new HaversineFormulaServiceConfigurationProperties();
        haversineFormulaServiceConfigurationProperties.setLat(61.217904);
        haversineFormulaServiceConfigurationProperties.setLon(-149.898384);
        haversineFormulaServiceConfigurationProperties.setRadiusInKilometers(10);

        subject = new HaversineFormulaService(haversineFormulaServiceConfigurationProperties);
    }

    @Test
    public void withinRadius_givenLatLonWithinRadius_returnTrue() {
        Boolean result = subject.withinRadius(61.21759217,-149.8935557);
        assertThat(result).isTrue();
    }

    @Test
    public void withinRadius_givenLatLonNotWithinRadius_returnFalse() {
        Boolean result = subject.withinRadius(33.65280991,-112.2374114);
        assertThat(result).isFalse();
    }
}
