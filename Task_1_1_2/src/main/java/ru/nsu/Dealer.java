package ru.nsu;

public class Dealer extends Player {

    final static int MAX = 19;

    Dealer(CardDeck deck) {
        super(deck);
        hand.getLast().setHidden(true);
    }

    /**
     * Method that reveals dealer's hidden card.
     */
    void openCard() {
        hand.getLast().setHidden(false);
    }
}