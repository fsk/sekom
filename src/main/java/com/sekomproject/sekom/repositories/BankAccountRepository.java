package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.BankAccount;
import com.sekomproject.sekom.entities.BankAccountOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
