package com.ironbank.bankingsystem.model.accounts;

import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class CheckingAccount extends Account {

    private BigDecimal minimumBalance = new BigDecimal("250");
    private BigDecimal monthlyMaintenanceFee = new BigDecimal("12");
    private final BigDecimal PENALTY_FEE = new BigDecimal("40");

    // Stateful default constructor
    public CheckingAccount(BigDecimal balance, String secretKey,
                           AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, primaryOwner, secondaryOwner, AccountStatus.ACTIVE);
    }

    // Constructor with explicit state
    public CheckingAccount(BigDecimal balance, String secretKey,
                           AccountHolder primaryOwner, AccountHolder secondaryOwner,
                           AccountStatus status) {
        super(balance, secretKey, primaryOwner, secondaryOwner, status);
    }

    // Extended constructor with minimumBalance and monthlyMaintenanceFee
    public CheckingAccount(BigDecimal balance, String secretKey,
                           AccountHolder primaryOwner, AccountHolder secondaryOwner,
                           BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee) {
        super(balance, secretKey, primaryOwner, secondaryOwner, AccountStatus.ACTIVE);
        setMinimumBalance(minimumBalance);
        setMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        if (minimumBalance.compareTo(new BigDecimal("250")) >= 0) {
            this.minimumBalance = minimumBalance;
        } else {
            throw new IllegalArgumentException("Minimum balance cannot be lower than 250");
        }
    }

    public void setMonthlyMaintenanceFee(BigDecimal fee) {
        if (fee.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Monthly maintenance fee cannot be negative");
        }
        this.monthlyMaintenanceFee = fee;
    }
}