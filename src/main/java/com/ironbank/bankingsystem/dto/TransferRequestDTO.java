package com.ironbank.bankingsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequestDTO {
    private Long originAccountId;
    private Long destinationAccountId;
    private BigDecimal amount;
    private String destinationOwnerName;
}