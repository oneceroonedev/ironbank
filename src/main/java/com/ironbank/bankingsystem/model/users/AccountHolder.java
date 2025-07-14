package com.ironbank.bankingsystem.model.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "account_holder")
@Getter
@Setter
public class AccountHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private String username;  // Added username field

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street", column=@Column(name="primary_street")),
            @AttributeOverride(name="city", column=@Column(name="primary_city")),
            @AttributeOverride(name="postalCode", column=@Column(name="primary_postal_code")),
            @AttributeOverride(name="country", column=@Column(name="primary_country"))
    })
    private Address primaryAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street", column=@Column(name="mailing_street")),
            @AttributeOverride(name="city", column=@Column(name="mailing_city")),
            @AttributeOverride(name="postalCode", column=@Column(name="mailing_postal_code")),
            @AttributeOverride(name="country", column=@Column(name="mailing_country"))
    })
    private Address mailingAddress;
}