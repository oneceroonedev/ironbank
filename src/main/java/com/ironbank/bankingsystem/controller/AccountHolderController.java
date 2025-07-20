package com.ironbank.bankingsystem.controller;

import com.ironbank.bankingsystem.dto.TransferRequestDTO;
import com.ironbank.bankingsystem.service.AccountHolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account-holder")
@RequiredArgsConstructor
public class AccountHolderController {

    private final AccountHolderService accountHolderService;

    // Check account balance
    @GetMapping("/accounts/{id}/balance")
    public BigDecimal getMyAccountBalance(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        return accountHolderService.getMyAccountBalance(id, userDetails.getUsername());
    }

    // Transfer between accounts
    @PostMapping("/transfer")
    public void transferMoney(@RequestBody TransferRequestDTO dto,
                              @AuthenticationPrincipal UserDetails userDetails) {
        accountHolderService.transferFunds(userDetails.getUsername(), dto);
    }
}