package com.sekomproject.sekom.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
public class BankAccountOwner extends MyMappedSuperClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", unique = true, nullable = false, length = 13)
    private String phoneNumber;

    @Column(name = "identity_number", nullable = false, unique = true, updatable = false, length = 11)
    private String identityNumber;

    @Column(name = "unique_account_owner_number")
    private UUID uniqueAccountOwnerNumber;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bank_account_owner_banks",
            joinColumns = @JoinColumn(name = "bank_account_owner_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_id"))
    private Set<Bank> banks = new HashSet<>();

    @OneToMany(mappedBy = "bankAccountOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccount> accounts = new ArrayList<>();

}
