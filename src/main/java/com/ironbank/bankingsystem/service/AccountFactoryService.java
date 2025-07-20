package com.ironbank.bankingsystem.service;

import com.ironbank.bankingsystem.model.accounts.Account;
import com.ironbank.bankingsystem.model.accounts.CheckingAccount;
import com.ironbank.bankingsystem.model.accounts.StudentChecking;
import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import com.ironbank.bankingsystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountFactoryService {

    private final AccountRepository accountRepository;

    /**
     * Creates a StudentChecking account if the account holder is under 24,
     * or a CheckingAccount if the account holder is 24 or older.
     */
    public Account createCheckingOrStudentAccount(BigDecimal initialBalance,
                                                  String secretKey,
                                                  AccountHolder primaryOwner,
                                                  AccountHolder secondaryOwner) {

        Account account;

        if (primaryOwner.getAge() < 24) {
            account = new StudentChecking(initialBalance, secretKey, primaryOwner);
            System.out.println("📘 StudentChecking created for users under 24");
        } else {
            CheckingAccount checkingAccount = new CheckingAccount(initialBalance, secretKey, primaryOwner, secondaryOwner);
            checkingAccount.setMinimumBalance(new BigDecimal("250")); // Valores por defecto
            checkingAccount.setMonthlyMaintenanceFee(new BigDecimal("12"));
            account = checkingAccount;
            System.out.println("🏦 CheckingAccount created for user over 24");
        }

        account.setStatus(AccountStatus.ACTIVE);
        return accountRepository.save(account);
    }
}