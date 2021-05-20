package com.tq.smartmeterapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tq.smartmeterapi.model.Address;
import com.tq.smartmeterapi.model.Client;
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
public class MeterReadingsTests {

    @Autowired
    private MeterReadingsController meterReadingsController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() throws Exception {
        assertThat(meterReadingsController).isNotNull();
    }

    @Test
    public void shouldCreateMeterReading_CREATED() throws Exception {
       MeterReadings meterReadings = new MeterReadings(new Date(), 1350.1);
       Meter meter = new Meter(1146548);
       meterReadings.setMeter(meter);

        this.mockMvc.perform(post("/api/meter-reading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meterReadings))).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void shouldCreateTwoMeterReadingSameDay_NotAccepptable() throws Exception {
        Date dateCreated = new Date();
        MeterReadings meterReadings = new MeterReadings(dateCreated, 1350.1);
        Meter meter = new Meter(1146548);
        meterReadings.setMeter(meter);


        this.mockMvc.perform(post("/api/meter-reading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meterReadings))).andDo(print()).andExpect(status().isCreated());

        MeterReadings meterReadings2 = new MeterReadings(dateCreated, 1150.0);
        meterReadings2.setMeter(meter);

        this.mockMvc.perform(post("/api/meter-reading")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(meterReadings2))).andDo(print()).andExpect(status().isNotAcceptable());
    }

}
