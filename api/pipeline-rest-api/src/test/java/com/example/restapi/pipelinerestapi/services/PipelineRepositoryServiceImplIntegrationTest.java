package com.example.restapi.pipelinerestapi.services;

import com.example.restapi.pipelinerestapi.dao.Starbucks;
import com.example.restapi.pipelinerestapi.repository.PipelineRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PipelineRepositoryServiceImplIntegrationTest {
    @Autowired
    private PipelineRepositoryService subject;

    @Autowired
    private PipelineRepository pipelineRepository;

    @Test
    public void getListByState_givenValidStateAbreviation_returnsListOfStarbucks() {
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

        List<Starbucks> result = subject.getListByState("CA");

        assertThat(result).contains(firstStarbucks, secondStarbucks);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getListByState_givenInvalidLongStateAbbreviation_throwsIllegalArgumentException() {
        subject.getListByState("THISISTOOLONG");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getListByState_givenInvalidShortStateAbbreviation_throwsIllegalArgumentException() {
        subject.getListByState("T");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getListByState_givenNullStateAbbreviation_throwsIllegalArgumentException() {
        subject.getListByState(null);
    }
}