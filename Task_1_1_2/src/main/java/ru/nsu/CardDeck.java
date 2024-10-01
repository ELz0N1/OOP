package ru.nsu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * Deck of playing cards implementation.
 */
public class CardDeck {

    ArrayList<Card> cards;

    CardDeck() {
        cards = new ArrayList<>();
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
     * @throws NoSuchElementException if deck is already empty and there is no card to pick.
     */
    public Card pickCard() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException("deck is empty");
        }
        // return cards.removeLast();
        return cards.remove(cards.size() - 1);

    }
}