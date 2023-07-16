package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccountOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountOwnerRepository extends JpaRepository<BankAccountOwner, Long> {

    Optional<BankAccountOwner> findByUniqueAccountOwnerNumber(UUID uniqueAccountOwnerNumber);
    @Query("SELECT ba.bank FROM BankAccount ba WHERE ba.bankAccountOwner.identityNumber = :identityNumber")
    List<Bank> findAllBanksByOwnerIdentityNumber(@Param("identityNumber") String identityNumber);

    Optional<BankAccountOwner> findByIdentityNumber(String identityNumber);


}
