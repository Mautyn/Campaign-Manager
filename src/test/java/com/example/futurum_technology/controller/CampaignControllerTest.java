package com.example.futurum_technology.controller;

import com.example.futurum_technology.model.Campaign;
import com.example.futurum_technology.service.CampaignService;
import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CampaignController.class)
class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CampaignService campaignService;

    @Test
    void shouldReturnAllCampaigns() throws Exception {
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setName("Test Campaign");

        when(campaignService.getAllCampaigns()).thenReturn(List.of(campaign));

        mockMvc.perform(get("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Campaign"));
    }

    @Test
    void shouldCreateCampaignSuccessfully() throws Exception {
        Campaign requestCampaign = new Campaign();
        requestCampaign.setName("New Campaign");
        requestCampaign.setKeywords("test, keyword");
        requestCampaign.setBidAmount(new BigDecimal("5.00"));
        requestCampaign.setCampaignFund(new BigDecimal("100.00"));
        requestCampaign.setStatus(true);
        requestCampaign.setRadius(10);

        Campaign savedCampaign = new Campaign();
        savedCampaign.setId(1L);
        savedCampaign.setName("New Campaign");

        when(campaignService.createCampaign(any(Campaign.class), eq(1L))).thenReturn(savedCampaign);

        mockMvc.perform(post("/api/campaigns")
                        .param("accountId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCampaign)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Campaign"));
    }

    @Test
    void shouldReturnBadRequest_whenValidationFails() throws Exception {
        Campaign invalidCampaign = new Campaign();

        mockMvc.perform(post("/api/campaigns")
                        .param("accountId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCampaign)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campaign name is mandatory"))
                .andExpect(jsonPath("$.bidAmount").value("Bid amount is mandatory"));
    }
}