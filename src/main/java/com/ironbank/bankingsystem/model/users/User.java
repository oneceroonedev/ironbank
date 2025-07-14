package com.ironbank.bankingsystem.model.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class User {

    // Getters y setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private LocalDate birthDate;

    // Empty constructor
    public User() {}

    // Full constructor
    public User(String name, String username, LocalDate birthDate) {
        this.name = name;
        this.username = username;
        this.birthDate = birthDate;
    }

}