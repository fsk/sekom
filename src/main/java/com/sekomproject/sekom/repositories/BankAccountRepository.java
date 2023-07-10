package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
