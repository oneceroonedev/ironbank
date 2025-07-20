package com.ironbank.bankingsystem.service;

import com.ironbank.bankingsystem.dto.TransferRequestDTO;
import com.ironbank.bankingsystem.model.accounts.Account;
import com.ironbank.bankingsystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountHolderService {

    private final AccountRepository accountRepository;

    // Check balance
    public BigDecimal getMyAccountBalance(Long accountId, String username) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!account.getPrimaryOwner().getUsername().equals(username) &&
                (account.getSecondaryOwner() == null ||
                        !account.getSecondaryOwner().getUsername().equals(username))) {
            throw new SecurityException("Access denied: not your account");
        }

        return account.getBalance();
    }

    // Transfer money
    public void transferFunds(String username, TransferRequestDTO dto) {
        Account origin = accountRepository.findById(dto.getOriginAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de origen no encontrada"));

        if (!origin.getPrimaryOwner().getUsername().equals(username) &&
                (origin.getSecondaryOwner() == null ||
                        !origin.getSecondaryOwner().getUsername().equals(username))) {
            throw new SecurityException("No tienes acceso a esta cuenta.");
        }

        if (origin.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new IllegalArgumentException("Fondos insuficientes.");
        }

        Account destination = accountRepository.findById(dto.getDestinationAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de destino no encontrada."));

        boolean validOwner =
                destination.getPrimaryOwner().getName().equalsIgnoreCase(dto.getDestinationOwnerName()) ||
                        (destination.getSecondaryOwner() != null &&
                                destination.getSecondaryOwner().getName().equalsIgnoreCase(dto.getDestinationOwnerName()));

        if (!validOwner) {
            throw new IllegalArgumentException("El nombre del propietario de la cuenta destino no coincide.");
        }

        // Transfer
        origin.setBalance(origin.getBalance().subtract(dto.getAmount()));
        destination.setBalance(destination.getBalance().add(dto.getAmount()));

        accountRepository.save(origin);
        accountRepository.save(destination);
    }
}