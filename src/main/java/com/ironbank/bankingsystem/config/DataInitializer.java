package com.ironbank.bankingsystem.config;

import com.ironbank.bankingsystem.model.accounts.CheckingAccount;
import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import com.ironbank.bankingsystem.repository.AccountHolderRepository;
import com.ironbank.bankingsystem.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    // This method will run automatically at application startup
    @Bean
    CommandLineRunner initDatabase(AccountHolderRepository accountHolderRepo, AccountRepository accountRepo) {
        return args -> {
            // Create an AccountHolder
            AccountHolder user1 = new AccountHolder();
            user1.setName("Alice");
            user1.setDateOfBirth(LocalDate.of(1990, 1, 1));
            user1.setUsername("alice234");

            accountHolderRepo.save(user1);

            // Create a CheckingAccount
            CheckingAccount account1 = new CheckingAccount();
            account1.setBalance(new BigDecimal("2500.00"));
            account1.setSecretKey("superSecret123");  // Set a secret key
            account1.setPrimaryOwner(user1);
            account1.setStatus(AccountStatus.ACTIVE);

            accountRepo.save(account1);
        };
    }
}