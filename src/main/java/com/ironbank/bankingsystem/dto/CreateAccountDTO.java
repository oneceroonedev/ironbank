package com.ironbank.bankingsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateAccountDTO {
    private String accountType; // CHECKING, SAVINGS, STUDENT, CREDIT
    private BigDecimal balance;
    private String secretKey;
    private Long primaryOwnerId;
    private Long secondaryOwnerId;

    // Optional depending on the type of account
    private BigDecimal interestRate;
    private BigDecimal minimumBalance;
    private BigDecimal creditLimit;
}