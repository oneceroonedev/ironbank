package com.ironbank.bankingsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ThirdPartyTransactionDTO {
    @NotNull
    private Long accountId;

    @NotBlank
    private String secretKey;

    @NotNull
    private BigDecimal amount;
}