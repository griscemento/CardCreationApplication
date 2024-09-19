package com.ejercicio2.service;

import com.ejercicio2.dto.EmailRequest;
import com.ejercicio2.model.Card;
import com.ejercicio2.model.Purchase;
import com.ejercicio2.model.User;
import com.ejercicio2.persistence.PurchaseRepository;
import com.ejercicio2.persistence.implementation.IPurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseService implements IPurchaseRepository {

    private final PurchaseRepository purchaseRepository;
    private final EmailService emailService;
    private final UserService userService;

    public PurchaseService(PurchaseRepository purchaseRepository, EmailService emailService, UserService userService) {
        this.purchaseRepository = purchaseRepository;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Override
    public void createPurchase(Purchase purchase, Optional<Card> card) throws Exception {
        purchase.setCard(card.get());

        purchaseRepository.save(purchase);
        User user = userService.findUserByCardId(card.get().getId());

        //    notifyPurchaseToUser(createEmailRequest(user, card));
    }

    private EmailRequest createEmailRequest(User user, Optional<Card> card) {

        return EmailRequest.builder()
                .to(user.getEmail())
                .subject(emailService.subject)
                .body(emailService.body)
                .user(user)
                .card(card.get())
                .build();
    }

    private void notifyPurchaseToUser(EmailRequest emailRequest) throws Exception {
        emailService.sendEmail(emailRequest);
    }
}
