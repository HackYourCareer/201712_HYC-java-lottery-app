package com.sap.controller;

import com.sap.model.Participant;
import com.sap.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
public class LotteryController {
	@Autowired
	private ParticipantRepository participantRepository;

	@GetMapping({"/", "/welcome", "/home**"})
	private String welcome() {
		return "welcome";
	}

	@GetMapping("/participant_form")
	private ModelAndView partcipantForm() {
		return new ModelAndView("participant_form", "participant", new Participant());
	}

	@PostMapping("/participant_created")
	private ModelAndView participantCreated(@ModelAttribute Participant participant) {
		participantRepository.save(participant);
		return new ModelAndView("participant_created", "participant", participant);
	}

	@GetMapping("/participants")
//	@PreAuthorize("hasAnyRole('ADMIN')") //TODO: try ROLE_ADMIN
	private ModelAndView showParticipants()
	{
		final Iterable<Participant> participants = participantRepository.findAll();

		Participant p1 = new Participant(); p1.setName("aaa"); p1.setSurname("aaa");
		Participant p2 = new Participant(); p2.setName("bbb"); p2.setSurname("bbb");
		Participant p3 = new Participant(); p3.setName("ccc"); p3.setSurname("ccc");
		List<Participant> newParticipants = Arrays.asList(p1, p2, p3);

		return new ModelAndView("participants", "participants", newParticipants);
	}
	
	
	@GetMapping("/winner")
	private ModelAndView drawWinner()
	{
		final long participantCount = participantRepository.count();
		final long winnerId = drawWinnerId(participantCount);
		final Participant winner = participantRepository.findOne(winnerId);
		return new ModelAndView("winner", "winner", winner);
	}

	private long drawWinnerId(final long participantCount) {
		return ThreadLocalRandom.current().nextLong(1, participantCount +1);
	}


}
