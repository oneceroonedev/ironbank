package com.ironbank.bankingsystem.service;

import com.ironbank.bankingsystem.dto.CreateAccountDTO;
import com.ironbank.bankingsystem.model.accounts.*;
import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import com.ironbank.bankingsystem.model.users.User;
import com.ironbank.bankingsystem.repository.AccountHolderRepository;
import com.ironbank.bankingsystem.repository.AccountRepository;
import com.ironbank.bankingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountHolderRepository accountHolderRepository;

    public BigDecimal getAccountBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getBalance();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(CreateAccountDTO dto) {
        AccountHolder primary = accountHolderRepository.findById(dto.getPrimaryOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Primary owner not found"));

        AccountHolder secondary = null;
        if (dto.getSecondaryOwnerId() != null) {
            secondary = accountHolderRepository.findById(dto.getSecondaryOwnerId())
                    .orElseThrow(() -> new IllegalArgumentException("Secondary owner not found"));
        }

        Account account;
        switch (dto.getAccountType().toUpperCase()) {
            case "CHECKING" -> account = new CheckingAccount(
                    dto.getBalance(),
                    dto.getSecretKey(),
                    primary,
                    secondary,
                    AccountStatus.ACTIVE
            );
            case "SAVINGS" -> account = new Savings(
                    dto.getBalance(),
                    dto.getSecretKey(),
                    primary,
                    secondary,
                    AccountStatus.ACTIVE,
                    dto.getInterestRate(),
                    dto.getMinimumBalance()
            );
            case "STUDENT" -> account = new StudentChecking(
                    dto.getBalance(),
                    dto.getSecretKey(),
                    primary,
                    secondary,
                    AccountStatus.ACTIVE
            );
            case "CREDIT" -> account = new CreditCard(
                    dto.getBalance(),
                    dto.getSecretKey(),
                    primary,
                    secondary,
                    AccountStatus.ACTIVE,
                    dto.getCreditLimit(),
                    dto.getInterestRate()
            );
            default -> throw new IllegalArgumentException("Invalid account type");
        }

        return accountRepository.save(account);
    }

    public void changeAccountStatus(Long accountId, AccountStatus newStatus) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.setStatus(newStatus);
        accountRepository.save(account);
    }
}