package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.BankAccountOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountOwnerRepository extends JpaRepository<BankAccountOwner, Long> {

    Optional<BankAccountOwner> findByUniqueAccountOwnerNumber(UUID uniqueAccountOwnerNumber);

}
