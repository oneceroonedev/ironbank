package com.ironbank.bankingsystem.controller;

import com.ironbank.bankingsystem.dto.CreateAccountDTO;
import com.ironbank.bankingsystem.model.accounts.Account;
import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.User;
import com.ironbank.bankingsystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/accounts/{id}/balance")
    public BigDecimal getBalance(@PathVariable Long id) {
        return adminService.getAccountBalance(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return adminService.getAllAccounts();
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody CreateAccountDTO dto) {
        return adminService.createAccount(dto);
    }

    @PatchMapping("/accounts/{id}/status")
    public void changeAccountStatus(@PathVariable Long id, @RequestParam AccountStatus status) {
        adminService.changeAccountStatus(id, status);
    }
}