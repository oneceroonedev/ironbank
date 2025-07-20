package com.ironbank.bankingsystem;

import com.ironbank.bankingsystem.dto.TransferRequestDTO;
import com.ironbank.bankingsystem.model.accounts.CheckingAccount;
import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import com.ironbank.bankingsystem.repository.AccountRepository;
import com.ironbank.bankingsystem.service.AccountHolderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountHolderServiceTest {

    private AccountRepository accountRepository;
    private AccountHolderService accountHolderService;

    private CheckingAccount originAccount;
    private CheckingAccount destinationAccount;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountHolderService = new AccountHolderService(accountRepository);

        AccountHolder mario = new AccountHolder();
        mario.setUsername("mario");

        AccountHolder luigi = new AccountHolder();
        luigi.setUsername("luigi");

        originAccount = new CheckingAccount();
        originAccount.setId(1L);
        originAccount.setBalance(new BigDecimal("1000"));
        originAccount.setPrimaryOwner(mario);
        originAccount.setSecretKey("secret");
        originAccount.setStatus(AccountStatus.ACTIVE);

        destinationAccount = new CheckingAccount();
        destinationAccount.setId(2L);
        destinationAccount.setBalance(new BigDecimal("500"));
        destinationAccount.setPrimaryOwner(luigi);
        destinationAccount.setSecretKey("destsecret");
        destinationAccount.setStatus(AccountStatus.ACTIVE);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(originAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(destinationAccount));
    }

    @Test
    void transferFunds_successfulTransfer_updatesBalances() {
        TransferRequestDTO dto = new TransferRequestDTO();
        dto.setOriginAccountId(1L);
        dto.setDestinationAccountId(2L);
        dto.setDestinationOwnerName("luigi");
        dto.setAmount(new BigDecimal("200"));

        accountHolderService.transferFunds("mario", dto);

        assertEquals(new BigDecimal("800"), originAccount.getBalance());
        assertEquals(new BigDecimal("700"), destinationAccount.getBalance());

        verify(accountRepository, times(1)).save(originAccount);
        verify(accountRepository, times(1)).save(destinationAccount);
    }

    @Test
    void transferFunds_invalidOwner_throwsSecurityException() {
        TransferRequestDTO dto = new TransferRequestDTO();
        dto.setOriginAccountId(1L);
        dto.setDestinationAccountId(2L);
        dto.setDestinationOwnerName("luigi");
        dto.setAmount(new BigDecimal("100"));

        Exception exception = assertThrows(SecurityException.class, () -> {
            accountHolderService.transferFunds("bowser", dto); // Unauthorized user
        });

        assertTrue(exception.getMessage().contains("You don't have access"));
    }

    @Test
    void transferFunds_insufficientFunds_throwsIllegalArgumentException() {
        TransferRequestDTO dto = new TransferRequestDTO();
        dto.setOriginAccountId(1L);
        dto.setDestinationAccountId(2L);
        dto.setDestinationOwnerName("luigi");
        dto.setAmount(new BigDecimal("999999"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountHolderService.transferFunds("mario", dto);
        });

        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }
}