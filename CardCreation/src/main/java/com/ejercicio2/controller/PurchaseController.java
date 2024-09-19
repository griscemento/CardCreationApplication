package com.ejercicio2.controller;

import com.ejercicio2.model.Card;
import com.ejercicio2.model.Purchase;
import com.ejercicio2.service.CardService;
import com.ejercicio2.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final CardService cardService;


    public PurchaseController(PurchaseService purchaseService, CardService cardService) {
        this.purchaseService = purchaseService;
        this.cardService = cardService;
    }

    @PostMapping("/createPurchase")
    public ResponseEntity<String> createNewPurchase(@RequestBody Purchase purchase) throws Exception {
        Optional<Card> card = cardService.findCardById(purchase.getCard().getId());
        purchaseService.createPurchase(purchase, card);
        return new ResponseEntity<>("Purchase created successfully. Card Number: " + card.get().getPersonalAccountNumber() +
                " CCV: " + card.get().getCcv() +  " Item: "
                + purchase.getPurchase() + " - Price: $" + purchase.getPrice(),
                HttpStatus.OK);
    }


}
