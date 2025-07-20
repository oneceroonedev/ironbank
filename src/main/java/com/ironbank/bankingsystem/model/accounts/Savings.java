package com.ironbank.bankingsystem.model.accounts;

import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account {

    private BigDecimal interestRate = new BigDecimal("0.0025");
    private BigDecimal minimumBalance = new BigDecimal("1000");
    private final BigDecimal PENALTY_FEE = new BigDecimal("40");

    private LocalDate lastInterestAppliedDate;

    public Savings(BigDecimal balance, String secretKey,
                   AccountHolder primaryOwner, AccountHolder secondaryOwner,
                   AccountStatus status,
                   BigDecimal interestRate, BigDecimal minimumBalance) {
        super(balance, secretKey, primaryOwner, secondaryOwner, status);
        setInterestRate(interestRate);
        setMinimumBalance(minimumBalance);
        this.lastInterestAppliedDate = getCreationDate();
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(new BigDecimal("0.5")) <= 0) {
            this.interestRate = interestRate;
        } else {
            throw new IllegalArgumentException("Interest rate cannot be greater than 0.5");
        }
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        if (minimumBalance.compareTo(new BigDecimal("100")) >= 0) {
            this.minimumBalance = minimumBalance;
        } else {
            throw new IllegalArgumentException("Minimum balance cannot be lower than 100");
        }
    }
}