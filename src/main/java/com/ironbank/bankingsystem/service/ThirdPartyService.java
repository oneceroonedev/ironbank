package com.ironbank.bankingsystem.service;

import com.ironbank.bankingsystem.dto.ThirdPartyDTO;
import com.ironbank.bankingsystem.model.users.ThirdParty;
import com.ironbank.bankingsystem.repository.ThirdPartyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThirdPartyService {

    private final ThirdPartyRepository thirdPartyRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ThirdParty createThirdParty(ThirdPartyDTO dto) {
        if (thirdPartyRepository.findByHashedKey(dto.getHashedKey()).isPresent()) {
            throw new IllegalArgumentException("hashedKey already exists");
        }

        // Encrypt the password as for any user
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        ThirdParty thirdParty = new ThirdParty(
                dto.getName(),
                dto.getUsername(),
                encodedPassword,
                dto.getBirthDate(),
                dto.getHashedKey()
        );

        return thirdPartyRepository.save(thirdParty);
    }
}