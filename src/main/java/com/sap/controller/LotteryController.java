package com.sap.controller;

import com.sap.model.Participant;
import com.sap.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.ThreadLocalRandom;


@Controller
public class LotteryController {
	@Autowired
	private ParticipantRepository participantRepository;

	@GetMapping("/")

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
	private ModelAndView showParticipants()
	{
		final Iterable<Participant> participants = participantRepository.findAll();
		return new ModelAndView("participants", "participants", participants);
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
