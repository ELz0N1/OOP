package ru.nsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Class for testing Player.
 */
public class TestPlayer {

    @Test
    public void initPlayerTest() {
        CardDeck deck = new CardDeck();
        Player player = new Player(deck);
        // player.hand.removeLast();
        // player.hand.removeLast();
        player.hand.remove(player.hand.size() - 1);
        player.hand.remove(player.hand.size() - 1);
        Assertions.assertEquals(player.getSum(), 0);
        Card card = new Card(CardSuit.Spades, CardValue.Eight);
        player.addCard(card);
        Assertions.assertEquals(player.getSum(), 8);
    }
}

