package com.ironbank.bankingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironbank.bankingsystem.dto.TransferRequestDTO;
import com.ironbank.bankingsystem.model.accounts.CheckingAccount;
import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import com.ironbank.bankingsystem.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountHolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long accountId1;
    private Long accountId2;

    @BeforeEach
    void setup() {
        // Create dummy accounts if they don't exist (ideally use @DataJpaTest with testcontainers)
        if (accountRepository.count() == 0) {
            AccountHolder user = new AccountHolder("Mario", "mario", "password",
                    LocalDate.of(1990, 1, 1), null);

            CheckingAccount acc1 = new CheckingAccount(
                    new BigDecimal("1000"),
                    "secret123",
                    user,
                    null,
                    AccountStatus.ACTIVE
            );
            CheckingAccount acc2 = new CheckingAccount(
                    new BigDecimal("500"),
                    "secret456",
                    user,
                    null,
                    AccountStatus.ACTIVE
            );

            accountRepository.save(acc1);
            accountRepository.save(acc2);
            accountId1 = acc1.getId();
            accountId2 = acc2.getId();
        } else {
            accountId1 = accountRepository.findAll().get(0).getId();
            accountId2 = accountRepository.findAll().get(1).getId();
        }
    }

    @Test
    @WithUserDetails("mario") // Make sure you have this user loaded in UserDetailsService
    void canCheckOwnBalance() throws Exception {
        mockMvc.perform(get("/account-holder/accounts/" + accountId1 + "/balance"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("mario")
    void canTransferBetweenOwnAccounts() throws Exception {
        TransferRequestDTO dto = new TransferRequestDTO();
        dto.setOriginAccountId(accountId1);
        dto.setDestinationAccountId(accountId2);
        dto.setAmount(new BigDecimal("100"));
        dto.setDestinationOwnerName("Mario");

        mockMvc.perform(post("/account-holder/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("mario")
    void throwsIfInsufficientFunds() throws Exception {
        TransferRequestDTO dto = new TransferRequestDTO();
        dto.setOriginAccountId(accountId1);
        dto.setDestinationAccountId(accountId2);
        dto.setAmount(new BigDecimal("999999"));
        dto.setDestinationOwnerName("Mario");

        mockMvc.perform(post("/account-holder/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("❌ Insufficient funds: current balance"));
    }
}
