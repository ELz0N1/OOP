package ru.nsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class for testing Dealer.
 */
public class TestDealer {

    @Test
    public void initPlayerTest() {
        CardDeck deck = new CardDeck();
        Dealer dealer = new Dealer(deck);
        Assertions.assertTrue(dealer.hand.getLast().isHidden());
        dealer.openCard();
        Assertions.assertFalse(dealer.hand.getLast().isHidden());
    }
}

