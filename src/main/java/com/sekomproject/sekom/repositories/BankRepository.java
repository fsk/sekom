package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
