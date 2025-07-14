package com.ironbank.bankingsystem.model.users;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {
    private String street;
    private String city;
    private String postalCode;
    private String country;
}
