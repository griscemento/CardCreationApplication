package com.ejercicio2.service;

import com.ejercicio2.model.Card;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Data
public class CardRateOperationService {

    private double rate;

    public double getRate(Optional<Card> card) {
        LocalDate expirationDate = card.get().getExpirationDate();

        int day = expirationDate.getDayOfMonth();
        int month = expirationDate.getMonthValue();
        int year = expirationDate.getYear();

        if (isCardValid(card.get())) {
            return switch (card.get().getBrand()) {
                case "VISA" -> year / (double) month;
                case "NARA" -> day * 0.5;
                case "AMEX" -> month * 0.1;
                default -> throw new IllegalArgumentException("Unknown brand");
            };
        }
        return rate;
    }

    public boolean isCardValid(Card card) {
        return rate < 10000 && card.isValid();
    }
}
