package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query("""
SELECT SUM(b.balance) FROM BankAccount b WHERE b.bankAccountOwner.uniqueAccountOwnerNumber = :ownerUUID AND b.bank.bankName = :bankName AND b.accountNumber = :accountNumber
""")
    BigDecimal getTotalBalanceByOwnerUUIDAndBankName(@Param("ownerUUID") UUID ownerUUID,
                                                     @Param("bankName") String bankName,
                                                     @Param("accountNumber") String accountNumber);

    Optional<BankAccount> findByAccountNumber(String accountNumber);



}
