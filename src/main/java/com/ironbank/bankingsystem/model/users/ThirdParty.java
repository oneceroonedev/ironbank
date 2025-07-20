package com.ironbank.bankingsystem.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ThirdParty extends User {

    @Column(nullable = false, unique = true)
    private String hashedKey;

    public ThirdParty(String name, String username, String password, LocalDate birthDate, String hashedKey) {
        super(name, username, password, birthDate);
        this.hashedKey = hashedKey;
    }
}