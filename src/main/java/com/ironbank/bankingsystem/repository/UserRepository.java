package com.ironbank.bankingsystem.repository;

import com.ironbank.bankingsystem.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

