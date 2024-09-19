package com.ejercicio2.controller;

import com.ejercicio2.model.Card;
import com.ejercicio2.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/saveCard")
    private ResponseEntity<String> createCard(@RequestBody Card card) throws Exception {
        cardService.createCard(card);
        return new ResponseEntity<>("Card saved successfully", HttpStatus.OK);
    }

    @GetMapping("/operationalRate")
    private ResponseEntity<String> getOperationalRate(@RequestParam Long id){
        Optional<Card> foundCard = Optional.ofNullable(cardService.findCardById(id))
                .orElseThrow(NoSuchElementException::new);

        double rate = cardService.getOperationRate(id);

        return new ResponseEntity<>("Card rate: " + rate + ". Card brand: "
                + foundCard.get().getBrand(), HttpStatus.OK);
    }

    @GetMapping("/getAllPurchases")
    private ResponseEntity<String> getAllPurchasesByCard(@RequestParam Long id){
        Optional<Card> foundCard = Optional.ofNullable(cardService.findCardById(id))
                .orElseThrow(NoSuchElementException::new);
        return new ResponseEntity<>(format("Purchases done by Card {}, with CCV {}: ",
                foundCard.get().getBrand(), foundCard.get().getCcv(), foundCard.get().getPurchases()),
                HttpStatus.OK);
    }

}
