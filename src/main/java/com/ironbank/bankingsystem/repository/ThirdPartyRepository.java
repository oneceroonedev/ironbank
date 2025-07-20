package com.ironbank.bankingsystem.repository;

import com.ironbank.bankingsystem.model.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
    Optional<ThirdParty> findByHashedKey(String hashedKey);
}