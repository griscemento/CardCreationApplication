package com.ejercicio2.persistence.implementation;

import com.ejercicio2.model.Card;

import java.util.Optional;

public interface ICardRepository {

    void createCard(Card card) throws Exception;

    Optional<Card> findCardById(Long id);
}
