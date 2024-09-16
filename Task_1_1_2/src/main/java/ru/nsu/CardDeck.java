package ru.nsu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class CardDeck {

    List<Card> cards;

    CardDeck() {
        cards = new ArrayList<Card>();
        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                cards.add(new Card(suit, value));
            }
        }
        Collections.shuffle(cards);
    }

    /**
     * Picks top card from deck.
     *
     * @return picked card.
     */
    public Card pickCard() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException("deck is empty");
        }
        return cards.removeLast();
    }

}