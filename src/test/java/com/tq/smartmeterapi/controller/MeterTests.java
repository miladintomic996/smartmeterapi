package com.tq.smartmeterapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tq.smartmeterapi.model.Meter;
import com.tq.smartmeterapi.model.MeterReadings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MeterTests {

    @Autowired
    private MeterController meterController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() throws Exception {
        assertThat(meterController).isNotNull();
    }

    @Test
    public void shouldCreateMeter_CREATED() throws Exception {
       Meter meter = new Meter(15646843);

        this.mockMvc.perform(post("/api/meter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meter))).andDo(print()).andExpect(status().isCreated());
    }

}
