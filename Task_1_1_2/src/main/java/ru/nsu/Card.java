package ru.nsu;


/**
 * A playing card class.
 */
public class Card {

    CardSuit suit;
    CardValue value;
    private boolean hidden;

    /**
     * Method to get card value.
     *
     * @return Returns value of card.
     */
    CardValue getValue() {
        return this.value;
    }

    /**
     * Method to get visibility of card.
     *
     * @return Returns is card hidden.
     */
    boolean isHidden() {
        return this.hidden;
    }

    /**
     * Sets card to hidden.
     */
    void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }

    @Override
    public String toString() {
        if (hidden) {
            return "<закрытая карта>";
        }
        return String.format("%s %s %d", value.name, suit.name, value.value);
    }
}
