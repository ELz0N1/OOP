package ru.nsu;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.Blackjack.GameIo;
import ru.nsu.Blackjack.GameState;


public class TestGame {

    @Test
    void TestGetInput1() {
        Blackjack game = new Blackjack();
        GameIo console = game.console;
        InputStream in = new ByteArrayInputStream("9\n1\n".getBytes());
        Scanner scanner = new Scanner(in);

        boolean result = console.getInput(scanner);
        Assertions.assertTrue(result);
    }

    @Test
    void TestGetInput2() {
        Blackjack game = new Blackjack();
        GameIo console = game.console;
        InputStream in = new ByteArrayInputStream("4\n5\n0\n".getBytes());
        Scanner scanner = new Scanner(in);

        boolean result = console.getInput(scanner);
        Assertions.assertFalse(result);
    }

    @Test
    public void TestHandleDealer1() {
        Blackjack game = new Blackjack();
        game.state = new GameState();

        game.state.player.hand.remove(game.state.player.hand.size() - 1);
        game.state.player.hand.remove(game.state.player.hand.size() - 1);
        Card card1 = new Card(CardSuit.Clubs, CardValue.Queen);
        Card card2 = new Card(CardSuit.Diamonds, CardValue.Ace);
        game.state.dealer.addCard(card1);
        game.state.dealer.addCard(card2);

        game.state.dealer.hand.remove(game.state.dealer.hand.size() - 1);
        game.state.dealer.hand.remove(game.state.dealer.hand.size() - 1);
        Card card3 = new Card(CardSuit.Hearts, CardValue.Ten);
        Card card4 = new Card(CardSuit.Spades, CardValue.Eight);
        game.state.dealer.addCard(card3);
        game.state.dealer.addCard(card4);
        game.state.roundEnded = true;

        Result result = game.handleDealer();
        Assertions.assertEquals(Result.PlayerWon, result);
    }

    @Test
    public void TestHandleDealer2() {
        Blackjack game = new Blackjack();
        game.state = new GameState();

        game.state.player.hand.remove(game.state.player.hand.size() - 1);
        game.state.player.hand.remove(game.state.player.hand.size() - 1);

        game.state.dealer.hand.remove(game.state.dealer.hand.size() - 1);
        game.state.dealer.hand.remove(game.state.dealer.hand.size() - 1);
        Card card1 = new Card(CardSuit.Clubs, CardValue.Queen);
        Card card2 = new Card(CardSuit.Diamonds, CardValue.Ace);
        game.state.dealer.addCard(card1);
        game.state.dealer.addCard(card2);
        game.state.roundEnded = true;
        Result result = game.handleDealer();
        Assertions.assertEquals(Result.DealerWon, result);
    }

    @Test
    public void TestCheckState() {
        Blackjack game = new Blackjack();
        game.state = new GameState();

        game.state.player.hand.remove(game.state.player.hand.size() - 1);
        game.state.player.hand.remove(game.state.player.hand.size() - 1);

        game.state.dealer.hand.remove(game.state.dealer.hand.size() - 1);
        game.state.dealer.hand.remove(game.state.dealer.hand.size() - 1);

        Card card1 = new Card(CardSuit.Diamonds, CardValue.Ace);
        Card card2 = new Card(CardSuit.Clubs, CardValue.Jack);
        game.state.player.addCard(card1);
        game.state.player.addCard(card2);

        Card card3 = new Card(CardSuit.Hearts, CardValue.Ten);
        Card card4 = new Card(CardSuit.Spades, CardValue.Five);
        game.state.dealer.addCard(card3);
        game.state.dealer.addCard(card4);

        Assertions.assertEquals(Result.PlayerWon, game.checkState());
    }
}
