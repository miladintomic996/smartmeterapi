package com.tq.smartmeterapi.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tq.smartmeterapi.model.Address;
import com.tq.smartmeterapi.model.Client;
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
public class ClientControllerTests {

    @Autowired
    private ClientController clientController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() throws Exception {
        assertThat(clientController).isNotNull();
    }

    @Test
    public void shouldCreateClient_CREATED() throws Exception {
        Client client = new Client("John", "Doe");
        client.setDateChanged(new Date());
        Address address = new Address("USA","US","New York", "Test Street", "12", 446846);
        client.setAddress(address);

        this.mockMvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client))).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void shouldCreateClientWithSameAddress_NotAcceptable() throws Exception {
        Client client = new Client("John", "Doe");
        client.setDateChanged(new Date());
        Address address = new Address("USA","US","New York", "Test Street", "12", 446846);
        client.setAddress(address);

        this.mockMvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)));

        Client client2 = new Client("Mark", "Johnson");
        client2.setAddress(address);

        this.mockMvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client))).andDo(print()).andExpect(status().isNotAcceptable());
    }



}
