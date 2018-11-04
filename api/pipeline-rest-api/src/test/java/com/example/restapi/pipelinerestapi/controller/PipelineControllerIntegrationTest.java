package com.example.restapi.pipelinerestapi.controller;

import com.example.restapi.pipelinerestapi.PipelineRestApiApplication;
import com.example.restapi.pipelinerestapi.dao.Starbucks;
import com.example.restapi.pipelinerestapi.repository.PipelineRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PipelineRestApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PipelineControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private PipelineRepository pipelineRepository;

    private final String EXPECTED_RESULT = "[{\"latitude\":1.232,\"longitude\":2.423,\"storeName\":\"Starbucks - CA - Huntington Beach [W]  01019\",\"address\":\"store adddress 1\"},{\"latitude\":5.2322,\"longitude\":23.4333,\"storeName\":\"Starbucks - CA - Glendora [W]  00937\",\"address\":\"store adddress 2\"}]";

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders httpHeaders = new HttpHeaders();

    @Test
    public void givenValidStateAbbreviation_returnsAllMatchedStatePayloads() {
        Starbucks firstStarbucks = new Starbucks(
                1.232,
                2.423,
                "Starbucks - CA - Huntington Beach [W]  01019",
                "store adddress 1");
        Starbucks secondStarbucks = new Starbucks(
                5.2322,
                23.4333,
                "Starbucks - CA - Glendora [W]  00937",
                "store adddress 2");
        Starbucks thirdStarbucks = new Starbucks(
                56.21222,
                235.4354333,
                "Starbucks - ID - Boise [W]  00937",
                "store adddress 3");

        ArrayList<Starbucks> bakedStarbucksList = new ArrayList<>();
        bakedStarbucksList.add(firstStarbucks);
        bakedStarbucksList.add(secondStarbucks);

        pipelineRepository.saveAll(bakedStarbucksList);

        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/state/CA",
                HttpMethod.GET, entity, String.class);

        assertThat(responseEntity.getBody()).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void givenInvalidShortStateAbbreviation_returnsAllMatchedStatePayloads() {
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/state/H",
                HttpMethod.GET, entity, String.class);

        System.out.println(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void givenInvalidLongStateAbbreviation_returnsAllMatchedStatePayloads() {
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/state/HASDF",
                HttpMethod.GET, entity, String.class);

        System.out.println(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}