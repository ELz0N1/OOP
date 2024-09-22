package ru.nsu;

/**
 * Dealer class implementation.
 */
public class Dealer extends Player {

    final static int MAX = 19;

    Dealer(CardDeck deck) {
        super(deck);
//        hand.getLast().setHidden(true);
        hand.get(hand.size() - 1).setHidden(true);

    }

    /**
     * Method that reveals dealer's hidden card.
     */
    void openCard() {
        hand.get(hand.size() - 1).setHidden(false);
    }
}