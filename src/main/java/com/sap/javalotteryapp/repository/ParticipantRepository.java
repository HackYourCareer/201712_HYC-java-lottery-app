package com.sap.javalotteryapp.repository;

import com.sap.javalotteryapp.model.Participant;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {
	Participant findFirstByNameAndSurname(String name, String surname);
}
