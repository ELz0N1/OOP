package ru.nsu;

/**
 * Suits of playing cards.
 */
public enum CardSuit {
    Diamonds("Бубны"),
    Hearts("Червей"),
    Clubs("Треф"),
    Spades("Пик");
    final String name;

    CardSuit(String name) {
        this.name = name;
    }
}