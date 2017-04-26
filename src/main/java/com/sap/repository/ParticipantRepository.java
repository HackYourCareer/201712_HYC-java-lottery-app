package com.sap.repository;

import com.sap.model.Participant;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<Participant, Long>
{
	Participant findFirstByNameAndSurname(final String name, final String surname);
}
