package com.ironbank.bankingsystem.model.users;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {

    public Admin(String name, String username, String password, LocalDate birthDate) {
        super(name, username, password, birthDate);
    }
}