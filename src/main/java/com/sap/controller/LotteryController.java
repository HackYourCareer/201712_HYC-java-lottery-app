package com.sap.controller;

import com.sap.model.Participant;
import com.sap.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

	@GetMapping("/social_participant")
	private ModelAndView socialParticipant(OAuth2Authentication authentication) {
		Participant participant = new Participant();
		Map<String, String> details;
		details = (Map<String, String>) authentication.getUserAuthentication().getDetails();
		String givenName = details.get("given_name");
		String familyName = details.get("family_name");
		String picture = details.get("picture");

		participant.setName(givenName);
		participant.setSurname(familyName);
		participant.setIcon(picture);
		return new ModelAndView("social_participant", "participant", participant);
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
