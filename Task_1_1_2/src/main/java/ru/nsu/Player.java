package ru.nsu;

import java.util.ArrayList;

/**
 * Class that implements player in blackjack.
 */
public class Player {

    protected ArrayList<Card> hand;

    /**
     * Method to count points of cards in hand.
     *
     * @return Returns summary points of cards in hand.
     */
    int getSum() {
        int sum = 0;
        int aces = 0;
        for (Card card : hand) {
            sum += card.getValue().value;
            if (card.getValue() == CardValue.Ace) {
                aces++;
            }
        }
        if (sum > 21) {
            sum -= aces * 10;
        }
        return sum;
    }

    Player(CardDeck deck) {
        this.hand = new ArrayList<>();
        addCard(deck.pickCard());
        addCard(deck.pickCard());
    }

    /**
     * Takes card from card deck.
     *
     * @param card Card to be added in player's hand.
     */
    void addCard(Card card) {
        hand.add(card);
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}