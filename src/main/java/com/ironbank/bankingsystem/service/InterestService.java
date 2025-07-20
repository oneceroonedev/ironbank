package com.ironbank.bankingsystem.service;

import com.ironbank.bankingsystem.model.accounts.Account;
import com.ironbank.bankingsystem.model.accounts.CreditCard;
import com.ironbank.bankingsystem.model.accounts.Savings;
import com.ironbank.bankingsystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterestService {

    private final AccountRepository accountRepository;

    // Manual method for applying interest (used by the admin controller)
    public void applyInterestToAll() {
        log.info("🧾 Manual application of interest initiated by the administrator...");
        applyInterestToAccounts();
        log.info("✅ Manual interest application completed.");
    }

    // Automatic daily method at 2:00 AM
    @Scheduled(cron = "0 0 2 * * *")
    public void applyInterestToAccounts() {
        log.info("⏳ Starting automatic interest process...");

        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            if (account instanceof Savings savingsAccount) {
                savingsAccount.applyInterest();
                accountRepository.save(savingsAccount);
                log.info("💰 Interest applied to Savings ID {}", savingsAccount.getId());

            } else if (account instanceof CreditCard creditCard) {
                creditCard.applyInterest();
                accountRepository.save(creditCard);
                log.info("💳 Interest applied to CreditCard ID {}", creditCard.getId());
            }
        }

        log.info("✅ Interest process completed.");
    }
}