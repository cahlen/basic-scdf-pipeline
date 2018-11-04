package com.example.restapi.pipelinerestapi.controller;

import com.example.restapi.pipelinerestapi.dao.Starbucks;
import com.example.restapi.pipelinerestapi.services.PipelineRepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PipelineController.class, secure = false)
public class PipelineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PipelineRepositoryService pipelineRepositoryService;

    private final String EXPECTED_RESULT = "[{\"latitude\":1.232,\"longitude\":2.423,\"storeName\":\"Starbucks - CA - Huntington Beach [W]  01019\",\"address\":\"store adddress 1\"},{\"latitude\":5.2322,\"longitude\":23.4333,\"storeName\":\"Starbucks - CA - Glendora [W]  00937\",\"address\":\"store adddress 2\"}]";

    @Test
    public void givenValidStateAbbreviation_returnsAllMatchedStatePayloads() throws Exception {
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

        given(pipelineRepositoryService.getListByState(anyString())).willReturn(bakedStarbucksList);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/state/CA").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertThat(result.getResponse().getContentAsString()).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void givenEmptyParameter_returnsEmptyResult() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/state/").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEmpty();
    }
}
