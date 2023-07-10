package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
