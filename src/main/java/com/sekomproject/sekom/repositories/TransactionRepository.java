package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.Transaction;
import com.sekomproject.sekom.entities.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
select t from Transaction t, BankAccount ba, Bank b
where
t.type = :transactionType
and
t.bankAccount.id = :bankAccountId
and 
t.bank.id = :bankId
and
ba.bank.id = :bankId
and
ba.bankAccountOwner.id = :ownerId
and 
t.creationDate < :date
order by t.creationDate desc 
""")
    List<Transaction> findAllByOperations(@Param("transactionType") TransactionType transactionType,
                                          @Param("bankAccountId") Long bankAccountId,
                                          @Param("bankId") Long bankId,
                                          @Param("ownerId") Long ownerId,
                                          @Param("date")Date date
                                          );

}
