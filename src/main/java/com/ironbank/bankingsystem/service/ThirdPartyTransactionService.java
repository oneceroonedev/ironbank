package com.ironbank.bankingsystem.service;

import com.ironbank.bankingsystem.dto.ThirdPartyTransactionDTO;
import com.ironbank.bankingsystem.model.accounts.Account;
import com.ironbank.bankingsystem.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ThirdPartyTransactionService {

    private final AccountRepository accountRepository;

    @Transactional
    public void sendMoney(ThirdPartyTransactionDTO dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!account.getSecretKey().equals(dto.getSecretKey())) {
            throw new IllegalArgumentException("Invalid secret key");
        }

        account.setBalance(account.getBalance().add(dto.getAmount()));
        accountRepository.save(account);
    }

    @Transactional
    public void receiveMoney(ThirdPartyTransactionDTO dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!account.getSecretKey().equals(dto.getSecretKey())) {
            throw new IllegalArgumentException("Invalid secret key");
        }

        if (account.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(dto.getAmount()));
        accountRepository.save(account);
    }
}