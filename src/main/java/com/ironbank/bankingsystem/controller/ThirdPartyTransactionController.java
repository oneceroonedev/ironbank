package com.ironbank.bankingsystem.controller;

import com.ironbank.bankingsystem.dto.ThirdPartyTransactionDTO;
import com.ironbank.bankingsystem.model.users.ThirdParty;
import com.ironbank.bankingsystem.repository.ThirdPartyRepository;
import com.ironbank.bankingsystem.service.ThirdPartyTransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/third-party")
@RequiredArgsConstructor
public class ThirdPartyTransactionController {

    private final ThirdPartyTransactionService transactionService;
    private final ThirdPartyRepository thirdPartyRepository;

    // Read hashedKey from header
    private ThirdParty authenticate(HttpServletRequest request) {
        String hashedKey = request.getHeader("hashedKey");
        if (hashedKey == null) {
            throw new IllegalArgumentException("Missing hashedKey header");
        }

        return thirdPartyRepository.findByHashedKey(hashedKey)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hashedKey"));
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMoney(@RequestBody @Valid ThirdPartyTransactionDTO dto,
                                       HttpServletRequest request) {
        authenticate(request); // Just check hashedKey
        transactionService.sendMoney(dto);
        return ResponseEntity.ok("Money sent successfully.");
    }

    @PostMapping("/receive")
    public ResponseEntity<?> receiveMoney(@RequestBody @Valid ThirdPartyTransactionDTO dto,
                                          HttpServletRequest request) {
        authenticate(request);
        transactionService.receiveMoney(dto);
        return ResponseEntity.ok("Money received successfully.");
    }
}