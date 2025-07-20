package com.ironbank.bankingsystem.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "third_party")
@Getter
@Setter
@NoArgsConstructor
public class ThirdParty extends User {

    @Column(name = "hashed_key", nullable = false)
    private String hashedKey;

    public ThirdParty(String name, String username, String password, LocalDate birthDate, String hashedKey) {
        super(name, username, password, birthDate);
        this.hashedKey = hashedKey;
    }
}