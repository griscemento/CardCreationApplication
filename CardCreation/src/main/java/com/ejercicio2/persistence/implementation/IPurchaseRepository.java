package com.ejercicio2.persistence.implementation;

import com.ejercicio2.model.Card;
import com.ejercicio2.model.Purchase;

import java.util.Optional;


public interface IPurchaseRepository {
    void createPurchase(Purchase purchase, Optional<Card> card) throws Exception;

}
