package at.spengergasse.fivepanels.service;

import at.spengergasse.fivepanels.model.common.Content;
import at.spengergasse.fivepanels.model.doctor.Doctor;
import at.spengergasse.fivepanels.model.medicalcase.*;
import at.spengergasse.fivepanels.model.messenger.Chat;
import at.spengergasse.fivepanels.repository.DoctorRepository;
import at.spengergasse.fivepanels.repository.MedicalcaseRepository;
import at.spengergasse.fivepanels.security.AuthenticatedUser;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static at.spengergasse.fivepanels.foundation.Assert.*;
import static at.spengergasse.fivepanels.foundation.Assert.isGreaterThanOrEqual;

@Service
public class MedicalcaseService {

    MedicalcaseRepository medicalcaseRepository;

    DoctorRepository doctorRepository;

    AuthenticatedUser authenticatedUser;

    public MedicalcaseService(MedicalcaseRepository medicalcaseRepository, DoctorRepository doctorRepository, AuthenticatedUser authenticatedUser) {
        this.medicalcaseRepository = medicalcaseRepository;
        this.doctorRepository = doctorRepository;
        this.authenticatedUser = authenticatedUser;
    }

    public void setCorrectAnswer(UUID id, Answer correctAnswer) {
        Optional<Medicalcase> medicalcaseOptional = medicalcaseRepository.findById(id);
        if (medicalcaseOptional.isPresent()) {
            Medicalcase medicalcase = medicalcaseOptional.get();
            if (!medicalcase.isPublished())
                throw new MedicalcaseException(STR."setCorrectAnswer(): can not set correctAnswer in non public medicalcase");
            isNotNull(correctAnswer, "correctAnswer");
            if (!(medicalcase.getVotingOptions().contains(correctAnswer))) {
                throw new MedicalcaseException(STR."setCorrectAnswer(): correctAnswer has to be in the votingOption!");
            }
            medicalcase.setCorrectAnswer(correctAnswer);
            medicalcaseRepository.save(medicalcase);
        }
    }

    public void evaluateVotesById(UUID id) {
        Optional<Medicalcase> medicalcaseOptional = medicalcaseRepository.findById(id);
        if (medicalcaseOptional.isPresent()) {
            Medicalcase medicalcase = medicalcaseOptional.get();
            //gibt die User zurück die für die richtige antwort gevotet haben.
            Map<UUID, Vote> userWithCorrectAnswer = medicalcase.getVotes().entrySet().stream()
                    .flatMap(entry -> entry.getValue().stream()
                            .filter(vote -> medicalcase.getCorrectAnswer().getAnswer().equals(vote.getAnswer().getAnswer()))
                            .map(vote -> Map.entry(entry.getKey(), vote)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            //addiert die Prozentzahl die der user für die richtige antwort angegeben hat zu seinem rating.
            // was ich gemacht habe:
            //userAndCorrectAnswer.entrySet().forEach(value -> UserRepository.findById(value.getKey()).ifPresent(user -> user.getProfile().setRating(value.getValue().getPercentage())));
            //Verbessert durch IDE
            userWithCorrectAnswer.forEach((key, value1) -> doctorRepository.findById(key).ifPresent(user -> user.getProfile().addRating(value1.getPercentage())));
            medicalcaseRepository.save(medicalcase);
        }
    }
}
