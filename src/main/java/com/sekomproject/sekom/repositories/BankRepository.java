package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;


public interface BankRepository extends JpaRepository<Bank, Long> {

    Optional<Bank> findByBankName(String bankName);

    @Query("""
select bank from Bank bank , BankAccount bankAccount where bankAccount.bankAccountOwner.identityNumber=?1
""")
    List<Bank> findAllByIdentityNumber(String identityNumber);

}
