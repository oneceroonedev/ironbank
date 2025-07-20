package com.ironbank.bankingsystem.controller;

import com.ironbank.bankingsystem.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/interests")
@RequiredArgsConstructor
public class InterestController {

    private final InterestService interestService;

    // Manual endpoint to apply interest to all eligible accounts
    @PostMapping("/apply")
    public ResponseEntity<String> applyInterestManually() {
        interestService.applyInterestToAll();
        return ResponseEntity.ok("Interest applied manually.");
    }
}