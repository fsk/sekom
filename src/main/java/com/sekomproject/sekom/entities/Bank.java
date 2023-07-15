package com.sekomproject.sekom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bank extends MyMappedSuperClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_name", unique = true)
    private String bankName;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private Set<BankAccount> bankAccounts = new HashSet<>();

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

}
