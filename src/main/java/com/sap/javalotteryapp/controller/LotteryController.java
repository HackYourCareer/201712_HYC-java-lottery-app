package com.sap.javalotteryapp.controller;

import com.sap.javalotteryapp.model.Participant;
import com.sap.javalotteryapp.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class LotteryController {

	@Autowired
	private ParticipantRepository participantRepository;

	@GetMapping("/")
	private String welcome() {
		return "welcome";
	}

	@GetMapping("/participantForm")
	private ModelAndView participantForm(OAuth2Authentication authentication) {
		Participant participant = createParticipant(authentication);

		return new ModelAndView("participant_form", "participant", participant);
	}


	@PostMapping("/participantCreated")
	private ModelAndView participantCreated(OAuth2Authentication authentication) throws Exception {
		Participant participant = createParticipant(authentication);

		Participant firstByNameAndSurname = participantRepository.findFirstByNameAndSurname(participant.getName(), participant.getSurname());

		if (firstByNameAndSurname != null)
		{
			throw new Exception("Nie ma takiego oszukiwania! :<");
		}

		participantRepository.save(participant);
		return new ModelAndView("participant_created", "participant" ,participant);
	}

	private Participant createParticipant(final OAuth2Authentication authentication) {
		Map<String, String> details = (Map<String, String>) authentication.getUserAuthentication().getDetails();

		String name = details.get("given_name");
		String surname = details.get("family_name");
		String iconUri = details.get("picture");

		return new Participant(name, surname, iconUri);
	}

	@GetMapping("/participants")
	private ModelAndView participants() {
		Iterable<Participant> participants = participantRepository.findAll();
		return new ModelAndView("participants", "participants", participants);
	}

	@GetMapping("/winner")
	private ModelAndView drawWinner() {
		long participantCount = participantRepository.count();
		long winnerId = drawWinnerId(participantCount);
		Participant winner = participantRepository.findOne(winnerId);
		return new ModelAndView("winner", "winner", winner);
	}

	private long drawWinnerId(final long participantCount) {
		return ThreadLocalRandom.current().nextLong(1, participantCount + 1);
	}
}
