package com.example.futurum_technology.service;

import com.example.futurum_technology.model.Campaign;
import com.example.futurum_technology.model.EmeraldAccount;
import com.example.futurum_technology.repository.CampaignRepository;
import com.example.futurum_technology.repository.EmeraldAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final EmeraldAccountRepository emeraldAccountRepository;

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with id: " + id));
    }

    @Transactional
    public Campaign createCampaign(Campaign campaign, Long accountId) {
        EmeraldAccount account = emeraldAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Emerald account not found"));

        BigDecimal fundToDeduct = campaign.getCampaignFund();
        if (account.getBalance().compareTo(fundToDeduct) < 0) {
            throw new RuntimeException("Insufficient funds in Emerald account");
        }

        account.setBalance(account.getBalance().subtract(fundToDeduct));
        emeraldAccountRepository.save(account);

        campaign.setEmeraldAccount(account);
        return campaignRepository.save(campaign);
    }

    public Campaign updateCampaign(Long id, Campaign updatedData) {
        Campaign existingCampaign = getCampaignById(id);

        existingCampaign.setName(updatedData.getName());
        existingCampaign.setKeywords(updatedData.getKeywords());
        existingCampaign.setBidAmount(updatedData.getBidAmount());
        existingCampaign.setStatus(updatedData.getStatus());
        existingCampaign.setTown(updatedData.getTown());
        existingCampaign.setRadius(updatedData.getRadius());

        return campaignRepository.save(existingCampaign);
    }

    public void deleteCampaign(Long id) {
        if (!campaignRepository.existsById(id)) {
            throw new RuntimeException("Campaign not found with id: " + id);
        }
        campaignRepository.deleteById(id);
    }
}