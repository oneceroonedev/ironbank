package com.ironbank.bankingsystem.model.accounts;

import com.ironbank.bankingsystem.model.users.AccountHolder;
import com.ironbank.bankingsystem.model.enums.AccountStatus;

import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class CheckingAccount extends Account {

    public CheckingAccount() {
        super();
    }

    public CheckingAccount(BigDecimal balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, AccountStatus status) {
        super(balance, secretKey, primaryOwner, secondaryOwner, status);
    }
}