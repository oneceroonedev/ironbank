package com.ironbank.bankingsystem.repository;

import com.ironbank.bankingsystem.model.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
    Optional<ThirdParty> findByHashedKey(String hashedKey);
    Optional<ThirdParty> findByUsername(String username);
}