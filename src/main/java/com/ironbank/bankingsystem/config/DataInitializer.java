package com.ironbank.bankingsystem.config;

import com.ironbank.bankingsystem.model.accounts.CheckingAccount;
import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import com.ironbank.bankingsystem.model.users.Admin;
import com.ironbank.bankingsystem.model.users.ThirdParty;
import com.ironbank.bankingsystem.repository.AccountHolderRepository;
import com.ironbank.bankingsystem.repository.AccountRepository;
import com.ironbank.bankingsystem.repository.AdminRepository;
import com.ironbank.bankingsystem.repository.ThirdPartyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AccountHolderRepository accountHolderRepo,
                                   AccountRepository accountRepo,
                                   AdminRepository adminRepo,
                                   ThirdPartyRepository thirdPartyRepo,
                                   PasswordEncoder passwordEncoder) {
        return args -> {

            // AccountHolder: mario
            if (accountHolderRepo.findByUsername("mario").isEmpty()) {
                AccountHolder mario = new AccountHolder();
                mario.setName("Mario");
                mario.setUsername("mario");
                mario.setPassword(passwordEncoder.encode("1234")); // Encrypted password
                mario.setBirthDate(LocalDate.of(1985, 3, 14));
                accountHolderRepo.save(mario);

                CheckingAccount checkingAccount = new CheckingAccount(
                        new BigDecimal("1000.00"),
                        "secret123",
                        mario,
                        null
                );
                checkingAccount.setStatus(AccountStatus.ACTIVE);
                accountRepo.save(checkingAccount);
            }

            // Admin user
            if (adminRepo.findByUsername("admin").isEmpty()) {
                Admin admin = new Admin();
                admin.setName("System Admin");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                adminRepo.save(admin);
            }

            // Third Party user
            if (thirdPartyRepo.findByUsername("thirdparty").isEmpty()) {
                ThirdParty thirdParty = new ThirdParty();
                thirdParty.setName("Trusted Third Party");
                thirdParty.setUsername("thirdparty");
                thirdParty.setPassword(passwordEncoder.encode("tp123"));
                thirdParty.setHashedKey("hashed-key-abc123");
                thirdPartyRepo.save(thirdParty);
            }

            System.out.println("✅ Test users initialized successfully.");
        };
    }
}