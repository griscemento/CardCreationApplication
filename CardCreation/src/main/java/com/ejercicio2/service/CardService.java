package com.ejercicio2.service;

import com.ejercicio2.dto.EmailRequest;
import com.ejercicio2.model.Card;
import com.ejercicio2.model.User;
import com.ejercicio2.persistence.CardRepository;
import com.ejercicio2.persistence.implementation.ICardRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CardService implements ICardRepository {

    private final CardRepository cardRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final CardRateOperationService cardRateOperationService;


    public CardService(CardRepository cardRepository, EmailService emailService,
                       UserService userService, CardRateOperationService cardRateOperationService) {
        this.cardRepository = cardRepository;
        this.emailService = emailService;
        this.userService = userService;
        this.cardRateOperationService = cardRateOperationService;
    }

    public double getOperationRate(Long id) {
        Optional<Card> foundCard = cardRepository.findById(id);
        if (foundCard.isEmpty()) {
            throw new NoSuchElementException("No card found");
        }
        return cardRateOperationService.getRate(foundCard);
    }


    @Override
    public void createCard(Card card) throws Exception {

        cardRepository.save(card);
        User user = userService.findUserByCardId(card.getId());

        //sendCardInformationToUser(createEmailRequest(user, card));
    }

    private EmailRequest createEmailRequest(User user, Card card) {

        return EmailRequest.builder()
                .to(user.getEmail())
                .subject(emailService.subject)
                .body(emailService.body)
                .user(user)
                .card(card)
                .build();
    }

    private void sendCardInformationToUser(EmailRequest emailRequest) throws Exception {
        emailService.sendEmail(emailRequest);
    }

    public Optional<Card> findCardById(Long id) {
        return cardRepository.findById(id);
    }
}
