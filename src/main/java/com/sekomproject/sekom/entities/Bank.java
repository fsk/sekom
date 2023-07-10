package com.sekomproject.sekom.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bank extends MyMappedSuperClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_name", unique = true)
    private String bankName;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccountOwner> owners = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(bankName, bank.bankName) && Objects.equals(owners, bank.owners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, bankName, owners);
    }
}
