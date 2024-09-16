package ru.nsu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.NoSuchElementException;


public class TestDeck {

    @Test
    void testDeck() {
        CardDeck deck = new CardDeck();
        for (int i = 0; i < 52; i++) {
            deck.pickCard();
        }
        Assertions.assertThrows(NoSuchElementException.class, deck::pickCard);
    }
}

