package ru.nsu;

/**
 * Suits of playing cards.
 */
enum CardSuit {
    Diamonds("Бубны"),
    Hearts("Червей"),
    Clubs("Треф"),
    Spades("Пик");
    final String name;

    CardSuit(String name) {
        this.name = name;
    }
}

/**
 * Ranks of playing cards.
 */
enum CardValue {
    Two(2, "Двойка"),
    Three(3, "Тройка"),
    Four(4, "Четвёрка"),
    Five(5, "Пятёрка"),
    Six(6, "Шестёрка"),
    Seven(7, "Семёрка"),
    Eight(8, "Восьмёрка"),
    Nine(9, "Девятка"),
    Ten(10, "Десятка"),
    Jack(10, "Валет"),
    Queen(10, "Дама"),
    King(10, "Король"),
    Ace(11, "Туз");
    final int value;
    final String name;

    CardValue(int value, String name) {
        this.value = value;
        this.name = name;
    }
}

/**
 * A playing card class.
 */
public class Card {

    private final CardSuit suit;
    private final CardValue value;
    private boolean hidden;

    CardValue getValue() {
        return this.value;
    }

    boolean isHidden() {
        return this.hidden;
    }

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
