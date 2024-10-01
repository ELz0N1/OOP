package ru.nsu;

/**
 * Ranks of playing cards.
 */
public enum CardValue {
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

