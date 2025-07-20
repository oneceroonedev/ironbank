package com.ironbank.bankingsystem.model.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "account_holder")
@Getter
@Setter
@NoArgsConstructor
public class AccountHolder extends User {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "primary_street")),
            @AttributeOverride(name = "city", column = @Column(name = "primary_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "primary_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "primary_country"))
    })
    private Address primaryAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "mailing_street")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mailing_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_country"))
    })
    private Address mailingAddress;

    public AccountHolder(String name, String username, String password, LocalDate birthDate, Address primaryAddress) {
        super(name, username, password, birthDate);
        this.primaryAddress = primaryAddress;
    }

    public AccountHolder(String name, String username, String password, LocalDate birthDate,
                         Address primaryAddress, Address mailingAddress) {
        super(name, username, password, birthDate);
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }
}