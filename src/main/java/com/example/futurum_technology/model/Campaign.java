package com.example.futurum_technology.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "campaigns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campaign name is mandatory")
    private String name;

    @NotBlank(message = "Keywords are mandatory")
    private String keywords;

    @NotNull(message = "Bid amount is mandatory")
    @DecimalMin(value = "1.00", message = "Minimum bid amount is 1.00")
    private BigDecimal bidAmount;

    @NotNull(message = "Campaign fund is mandatory")
    @DecimalMin(value = "10.00", message = "Minimum campaign fund is 10.00")
    private BigDecimal campaignFund;

    @NotNull(message = "Status is mandatory")
    private Boolean status;

    private String town;

    @NotNull(message = "Radius is mandatory")
    @Min(value = 1, message = "Radius must be at least 1 km")
    private Integer radius;

    @ManyToOne
    @JoinColumn(name = "emerald_account_id", nullable = false)
    private EmeraldAccount emeraldAccount;
}