package com.example.restapi.pipelinerestapi.services;

import com.example.restapi.pipelinerestapi.dao.Starbucks;
import com.example.restapi.pipelinerestapi.repository.PipelineRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PipelineRepositoryServiceImplTest {
    private PipelineRepositoryServiceImpl subject;

    @Mock
    private PipelineRepository mockPipelineRepository;

    @Before
    public void setUp() {
        subject = new PipelineRepositoryServiceImpl(mockPipelineRepository);
    }

    @Test
    public void getListByState_givenValidStateAbbreviation_returnsListOfStarbucksObjects() {
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

        ArrayList<Starbucks> bakedStarbucksList = new ArrayList<>();
        bakedStarbucksList.add(firstStarbucks);
        bakedStarbucksList.add(secondStarbucks);

        given(mockPipelineRepository.findStarbucksByStoreNameContains(any(String.class))).willReturn(bakedStarbucksList);

        List<Starbucks> result = subject.getListByState("CA");

        assertThat(result).contains(firstStarbucks,secondStarbucks);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getListByState_givenInvalidLongStateAbbreviation_throwIllegalArgumentException() {
        subject.getListByState("THISISTOOLONG");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getListByState_givenInvalidShortStateAbbreviation_throwIllegalArgumentException() {
        subject.getListByState("T");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getListByState_givenNullInputParameter_throwIllegalArgumentException() {
        subject.getListByState(null);
    }
}
