package com.ironbank.bankingsystem.model.accounts;

import com.ironbank.bankingsystem.model.enums.AccountStatus;
import com.ironbank.bankingsystem.model.users.AccountHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "account_type")
@Getter
public abstract class Account {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotNull
    @Column(nullable = false)
    private BigDecimal balance;

    @Setter
    @NotNull
    @Column(nullable = false)
    private String secretKey;

    @NotNull
    @Column(nullable = false)
    private LocalDate creationDate;

    @Setter
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "primary_owner_id", nullable = false)
    private AccountHolder primaryOwner;

    @Setter
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id")
    private AccountHolder secondaryOwner;

    // Default constructor
    public Account() {
        this.creationDate = LocalDate.now();
        this.status = AccountStatus.ACTIVE;
    }

    // Full constructor with explicit status
    public Account(@NotNull BigDecimal balance,
                   @NotNull String secretKey,
                   @NotNull AccountHolder primaryOwner,
                   AccountHolder secondaryOwner,
                   @NotNull AccountStatus status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = status;
        this.creationDate = LocalDate.now();
    }

    // Constructor with default status ACTIVE
    public Account(@NotNull BigDecimal balance,
                   @NotNull String secretKey,
                   @NotNull AccountHolder primaryOwner,
                   AccountHolder secondaryOwner) {
        this(balance, secretKey, primaryOwner, secondaryOwner, AccountStatus.ACTIVE);
    }

    // Equals and hashCode based on id

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}