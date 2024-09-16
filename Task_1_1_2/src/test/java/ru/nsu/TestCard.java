package ru.nsu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TestCard {

    static void assertStrEquals(Object obj1, Object obj2) {
        Assertions.assertEquals(obj1.toString(), obj2.toString());
    }

    @Test
    void testCard() {
        assertStrEquals("Двойка Бубны 2", new Card(CardSuit.Diamonds, CardValue.Two));
        assertStrEquals("Тройка Треф 3", new Card(CardSuit.Clubs, CardValue.Three));
        assertStrEquals("Четвёрка Пик 4", new Card(CardSuit.Spades, CardValue.Four));
        assertStrEquals("Пятёрка Червей 5", new Card(CardSuit.Hearts, CardValue.Five));
        assertStrEquals("Шестёрка Бубны 6", new Card(CardSuit.Diamonds, CardValue.Six));
        assertStrEquals("Семёрка Треф 7", new Card(CardSuit.Clubs, CardValue.Seven));
        assertStrEquals("Восьмёрка Пик 8", new Card(CardSuit.Spades, CardValue.Eight));
        assertStrEquals("Девятка Червей 9", new Card(CardSuit.Hearts, CardValue.Nine));
        assertStrEquals("Десятка Бубны 10", new Card(CardSuit.Diamonds, CardValue.Ten));
        assertStrEquals("Туз Червей 11", new Card(CardSuit.Hearts, CardValue.Ace));

        assertStrEquals("Валет Червей 10", new Card(CardSuit.Hearts, CardValue.Jack));
        assertStrEquals("Валет Бубны 10", new Card(CardSuit.Diamonds, CardValue.Jack));
        assertStrEquals("Валет Треф 10", new Card(CardSuit.Clubs, CardValue.Jack));
        assertStrEquals("Валет Пик 10", new Card(CardSuit.Spades, CardValue.Jack));

        assertStrEquals("Король Червей 10", new Card(CardSuit.Hearts, CardValue.King));
        assertStrEquals("Король Бубны 10", new Card(CardSuit.Diamonds, CardValue.King));
        assertStrEquals("Король Треф 10", new Card(CardSuit.Clubs, CardValue.King));
        assertStrEquals("Король Пик 10", new Card(CardSuit.Spades, CardValue.King));

        assertStrEquals("Дама Треф 10", new Card(CardSuit.Clubs, CardValue.Queen));
        assertStrEquals("Дама Пик 10", new Card(CardSuit.Spades, CardValue.Queen));
        assertStrEquals("Дама Червей 10", new Card(CardSuit.Hearts, CardValue.Queen));
        assertStrEquals("Дама Бубны 10", new Card(CardSuit.Diamonds, CardValue.Queen));
    }
}
