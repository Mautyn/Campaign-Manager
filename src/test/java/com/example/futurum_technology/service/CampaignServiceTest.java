package com.example.futurum_technology.service;

import com.example.futurum_technology.model.Campaign;
import com.example.futurum_technology.model.EmeraldAccount;
import com.example.futurum_technology.repository.CampaignRepository;
import com.example.futurum_technology.repository.EmeraldAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private EmeraldAccountRepository emeraldAccountRepository;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    void shouldCreateCampaignAndDeductFunds_whenFundsAreSufficient() {
        Long accountId = 1L;
        EmeraldAccount account = new EmeraldAccount(accountId, new BigDecimal("1000.00"));

        Campaign campaign = new Campaign();
        campaign.setName("Test Campaign");
        campaign.setCampaignFund(new BigDecimal("200.00"));

        when(emeraldAccountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(campaignRepository.save(any(Campaign.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Campaign result = campaignService.createCampaign(campaign, accountId);

        assertNotNull(result);
        assertEquals(account, result.getEmeraldAccount());

        assertEquals(new BigDecimal("800.00"), account.getBalance());

        verify(emeraldAccountRepository, times(1)).save(account);
        verify(campaignRepository, times(1)).save(campaign);
    }

    @Test
    void shouldThrowException_whenFundsAreInsufficient() {
        Long accountId = 1L;
        EmeraldAccount account = new EmeraldAccount(accountId, new BigDecimal("50.00"));

        Campaign campaign = new Campaign();
        campaign.setName("Too Expensive Campaign");
        campaign.setCampaignFund(new BigDecimal("200.00"));

        when(emeraldAccountRepository.findById(accountId)).thenReturn(Optional.of(account));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            campaignService.createCampaign(campaign, accountId);
        });

        assertEquals("Insufficient funds in Emerald account", exception.getMessage());

        verify(emeraldAccountRepository, never()).save(any());
        verify(campaignRepository, never()).save(any());
    }

    @Test
    void shouldUpdateCampaignSuccessfully() {
        Long campaignId = 1L;
        Campaign existingCampaign = new Campaign();
        existingCampaign.setId(campaignId);
        existingCampaign.setName("Old Name");

        Campaign updatedData = new Campaign();
        updatedData.setName("New Name");
        updatedData.setStatus(false);

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(existingCampaign));
        when(campaignRepository.save(any(Campaign.class))).thenAnswer(i -> i.getArgument(0));

        Campaign result = campaignService.updateCampaign(campaignId, updatedData);

        assertEquals("New Name", result.getName());
        assertFalse(result.getStatus());
        verify(campaignRepository, times(1)).save(existingCampaign);
    }
}