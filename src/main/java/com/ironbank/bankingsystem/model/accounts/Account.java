package com.ironbank.bankingsystem.model.accounts;

import com.ironbank.bankingsystem.model.users.AccountHolder;
import com.ironbank.bankingsystem.model.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@MappedSuperclass
public abstract class Account {

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
    private LocalDate creationDate = LocalDate.now(); // Initialize directly here

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

    // --- Constructors ---
    public Account() {
        this.status = AccountStatus.ACTIVE;  // Default status
    }

    public Account(@NotNull BigDecimal balance, @NotNull String secretKey, @NotNull AccountHolder primaryOwner,
                   AccountHolder secondaryOwner, AccountStatus status) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.status = status;
    }

    // --- equals and hashCode based on id ---

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