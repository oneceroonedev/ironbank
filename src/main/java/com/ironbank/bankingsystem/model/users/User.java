package com.ironbank.bankingsystem.model.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Soporta subclases en JPA
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // Necesario para login

    private LocalDate birthDate;

    // --- Constructors ---

    public User() {}

    public User(String name, String username, String password, LocalDate birthDate) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
    }
}