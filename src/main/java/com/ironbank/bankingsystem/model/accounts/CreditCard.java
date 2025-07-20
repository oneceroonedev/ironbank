package com.ironbank.bankingsystem.model.accounts;

import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account {

    private BigDecimal creditLimit = new BigDecimal("100");
    private BigDecimal interestRate = new BigDecimal("0.2");
    private final BigDecimal PENALTY_FEE = new BigDecimal("40");

    private LocalDate lastInterestAppliedDate;

    public CreditCard(BigDecimal balance, String secretKey,
                      AccountHolder primaryOwner, AccountHolder secondaryOwner,
                      AccountStatus status,
                      BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwner, status);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
        this.lastInterestAppliedDate = getCreationDate();
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        if (creditLimit.compareTo(new BigDecimal("100000")) <= 0) {
            this.creditLimit = creditLimit;
        } else {
            throw new IllegalArgumentException("Credit limit cannot be greater than 100,000");
        }
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(new BigDecimal("0.1")) >= 0) {
            this.interestRate = interestRate;
        } else {
            throw new IllegalArgumentException("Interest rate cannot be less than 0.1");
        }
    }

    public void applyInterest() {
        LocalDate now = LocalDate.now();

        if (lastInterestAppliedDate == null || lastInterestAppliedDate.plusMonths(1).isBefore(now)) {
            BigDecimal monthlyRate = interestRate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
            BigDecimal interest = getBalance().multiply(monthlyRate);
            setBalance(getBalance().add(interest));
            lastInterestAppliedDate = now;
        }
    }
}