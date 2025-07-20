package com.ironbank.bankingsystem.service;

import com.ironbank.bankingsystem.dto.TransferRequestDTO;
import com.ironbank.bankingsystem.model.accounts.*;
import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import com.ironbank.bankingsystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class AccountHolderService {

    private final AccountRepository accountRepository;

    // Create Checking or StudentChecking by age
    public Account createCheckingOrStudentAccount(BigDecimal balance, String secretKey,
                                                  AccountHolder primaryOwner, AccountHolder secondaryOwner,
                                                  AccountStatus status) {
        int age = calculateAge(primaryOwner.getBirthDate());

        Account account;
        if (age < 24) {
            account = new StudentChecking(balance, secretKey, primaryOwner, secondaryOwner, status);
        } else {
            account = new CheckingAccount(balance, secretKey, primaryOwner, secondaryOwner, status);
        }

        return accountRepository.save(account);
    }

    // Age Calculator
    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    // Check balance
    public BigDecimal getMyAccountBalance(Long accountId, String username) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found."));

        if (!isOwner(account, username)) {
            throw new SecurityException("Access denied: This account does not belong to you.");
        }

        return account.getBalance();
    }

    // Transfers
    public void transferFunds(String username, TransferRequestDTO dto) {
        Account origin = accountRepository.findById(dto.getOriginAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Source account not found."));

        if (!isOwner(origin, username)) {
            throw new SecurityException("You don't have access to this source account.");
        }

        BigDecimal currentBalance = origin.getBalance();
        BigDecimal transferAmount = dto.getAmount();

        if (currentBalance.compareTo(transferAmount) < 0) {
            throw new IllegalArgumentException("Insufficient funds: current balance " + currentBalance);
        }

        Account destination = accountRepository.findById(dto.getDestinationAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found."));

        if (!isOwnerNameMatching(destination, dto.getDestinationOwnerName())) {
            throw new IllegalArgumentException("The target account owner name does not match.");
        }

        // Execute transfer
        origin.setBalance(currentBalance.subtract(transferAmount));
        destination.setBalance(destination.getBalance().add(transferAmount));

        // Penalties
        applyPenaltyIfNeeded(origin);

        accountRepository.save(origin);
        accountRepository.save(destination);
    }

    // Verify ownership
    private boolean isOwner(Account account, String username) {
        return account.getPrimaryOwner().getUsername().equals(username) ||
                (account.getSecondaryOwner() != null &&
                        account.getSecondaryOwner().getUsername().equals(username));
    }

    // Check destination name
    private boolean isOwnerNameMatching(Account account, String expectedName) {
        return account.getPrimaryOwner().getName().equalsIgnoreCase(expectedName) ||
                (account.getSecondaryOwner() != null &&
                        account.getSecondaryOwner().getName().equalsIgnoreCase(expectedName));
    }

    // Low balance penalty
    private void applyPenaltyIfNeeded(Account account) {
        BigDecimal penaltyFee = new BigDecimal("40");

        if (account instanceof CheckingAccount checking) {
            BigDecimal minimum = checking.getMinimumBalance();
            if (checking.getBalance().compareTo(minimum) < 0) {
                System.out.println("⚠️ Penalty applied to CheckingAccount for low balance");
                checking.setBalance(checking.getBalance().subtract(penaltyFee));
            }
        } else if (account instanceof Savings savings) {
            BigDecimal minimum = savings.getMinimumBalance();
            if (savings.getBalance().compareTo(minimum) < 0) {
                System.out.println("⚠️ Penalty applied to Savings for low balance");
                savings.setBalance(savings.getBalance().subtract(penaltyFee));
            }
        }
    }
}