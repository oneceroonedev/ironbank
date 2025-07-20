package com.ironbank.bankingsystem.controller;

import com.ironbank.bankingsystem.dto.ThirdPartyDTO;
import com.ironbank.bankingsystem.model.users.ThirdParty;
import com.ironbank.bankingsystem.service.ThirdPartyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/third-party")
@RequiredArgsConstructor
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody @Valid ThirdPartyDTO dto) {
        return thirdPartyService.createThirdParty(dto);
    }
}