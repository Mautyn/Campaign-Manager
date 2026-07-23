package com.example.futurum_technology.controller;

import com.example.futurum_technology.model.Campaign;
import com.example.futurum_technology.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CampaignController {

    private final CampaignService campaignService;

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.getCampaignById(id));
    }

    @PostMapping
    public ResponseEntity<Campaign> createCampaign(
            @Valid @RequestBody Campaign campaign,
            @RequestParam Long accountId) {

        Campaign createdCampaign = campaignService.createCampaign(campaign, accountId);
        return new ResponseEntity<>(createdCampaign, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody Campaign campaign) {

        return ResponseEntity.ok(campaignService.updateCampaign(id, campaign));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }
}