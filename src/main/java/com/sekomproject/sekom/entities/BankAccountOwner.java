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
public class BankAccountOwner extends MyMappedSuperClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "identity_number", nullable = false, unique = true, updatable = false, length = 11)
    private String identityNumber;

    @Column(name = "unique_account_owner_number")
    private UUID uniqueAccountOwnerNumber;

    @OneToMany(mappedBy = "bankAccountOwner", cascade = CascadeType.ALL)
    private List<Transaction> transactions;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "communication_information_id")
    private CommunicationInformation communicationInformation;

    @OneToMany(mappedBy = "bankAccountOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccount> accounts = new ArrayList<>();

}
