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
public class StudentChecking extends Account {

    public StudentChecking(BigDecimal balance, String secretKey,
                           AccountHolder primaryOwner, AccountHolder secondaryOwner,
                           AccountStatus status) {
        super(balance, secretKey, primaryOwner, secondaryOwner, status);
    }

    public StudentChecking(BigDecimal balance, String secretKey, AccountHolder primaryOwner) {
        super(balance, secretKey, primaryOwner, null);
    }
}