package ru.nsu;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class for testing a deck.
 */
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

