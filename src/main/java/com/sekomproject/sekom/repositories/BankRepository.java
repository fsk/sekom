package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, Long> {

    Optional<Bank> findByBankName(String bankName);

    List<Bank> findByBankAccountOwnersUniqueAccountOwnerNumber(UUID uniqueAccountOwnerNumber);

}
