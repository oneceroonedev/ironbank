package com.ironbank.bankingsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO for account creation by the administrator.
 * Important Notes:
 * - For CHECKING accounts, age logic will be applied to decide whether to create a StudentChecking or CheckingAccount.
 * - The interestRate, minimumBalance, and creditLimit fields are only required for specific types.
 */
@Getter
@Setter
public class CreateAccountDTO {

    /**
     * Type of account to create. Valid values:
     * - "CHECKING" → a StudentChecking or CheckingAccount will be automatically created depending on the age.
     * - "SAVINGS"
     * - "CREDIT"
     */
    private String accountType;

    private BigDecimal balance;
    private String secretKey;

    private Long primaryOwnerId;
    private Long secondaryOwnerId;

    // Only necessary for SAVINGS
    private BigDecimal interestRate;
    private BigDecimal minimumBalance;

    // Only needed for CREDIT
    private BigDecimal creditLimit;
}