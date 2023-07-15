package com.sekomproject.sekom.repositories;

import com.sekomproject.sekom.entities.CommunicationInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationInformationRepository extends JpaRepository<CommunicationInformation, Long> {

    CommunicationInformation findByPhoneNumberAndEmail(String phoneNumber, String email);

}
