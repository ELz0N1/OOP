package ru.nsu;

import java.util.Scanner;

enum HandState {
    BlackJack,
    OverHead,
    NotEnough,
}

enum Turn {
    DealerTurn,
    PlayerTurn,
}

enum PlayerInput {
    Continue(1),
    Stop(0);
    final int value;

    PlayerInput(int value) {
        this.value = value;
    }
}

enum Result {
    NotDecided,
    PlayerWon,
    DealerWon,
    Draw,
}

class Blackjack {

    private class GameState {

        CardDeck deck;
        Player player;
        Dealer dealer;
        Turn turn;
        boolean roundEnded;
        Result result;

        GameState() {
            deck = new CardDeck();
            player = new Player(deck);
            dealer = new Dealer(deck);
            turn = Turn.PlayerTurn;
            roundEnded = false;
            result = Result.NotDecided;
        }
    }

    private class IO {

        Scanner in;
        private static final String askForInput =
            "Введите \"1\", чтобы взять карту, и \"0\", чтобы остановиться...";

        IO() {
            in = new Scanner(System.in);
        }

        boolean getInput() {
            System.out.println(askForInput);
            int input = in.nextInt();
            if (input == PlayerInput.Continue.value) {
                return true;
            } else if (input == PlayerInput.Stop.value) {
                return false;
            }
            System.out.println("Неправильный ввод, повторите ещё раз: ");
            return getInput();
        }

        void turnName() {
            String message = "";
            if (state.turn == Turn.PlayerTurn) {
                message += "Ваш ход\n";
            } else {
                message += "Ход дилера\n";
            }
            message += "-------\n";
            System.out.print(message);
        }

        void openCard(Card card) {
            String message = "";
            if (state.turn == Turn.PlayerTurn) {
                message += "Вы открыли карту " + card.toString();
            } else {
                message += "Дилер открывает ";
                if (card.isHidden()) {
                    message += "закрытую ";
                    state.dealer.openCard();
                }
                message += "карту " + card.toString();
            }
            System.out.print(message + "\n");
        }

        void printHands() {
            String message = "  Ваши карты: " + state.player.toString() +
                " ⇒ " + state.player.getSum() + "\n";
            message += "  Карты дилера: " + state.dealer.toString();
            if (state.turn == Turn.DealerTurn) {
                message += " ⇒ " + state.dealer.getSum();
            }
            System.out.print(message + "\n\n");
        }

        void roundStart() {
            String string = String.format("Раунд %d\nДилер раздал карты", round);
            System.out.println(string);
            printHands();
        }

        void printResult() {
            String message = "";
            if (state.result == Result.DealerWon) {
                message += "Дилер выйграл раунд! ";
            } else if (state.result == Result.PlayerWon) {
                message += "Вы выйграли раунд! ";
            } else {
                message += "Ничья! ";
            }
            message += " Счёт " + playerPoints + ":" +
                dealerPoints + " ";
            if (playerPoints > dealerPoints) {
                message += "в вашу пользу";
            } else if (dealerPoints > playerPoints) {
                message += "в пользу дилера";
            }
            System.out.println(message + ".\n");
        }
    }

    int round;
    int playerPoints;
    int dealerPoints;
    GameState state;
    IO console;

    Blackjack() {
        console = new IO();
        playerPoints = 0;
        dealerPoints = 0;
        round = 1;
    }

    void run() {
        System.out.println("Добро пожаловать в блэкджек!");
        while (true) {
            gameloop();
            round += 1;
        }
    }

    void gameloop() {
        state = new GameState();
        state.roundEnded = false;

        state.result = start();
        if (state.result != Result.NotDecided) {
            addPoints();
            console.printResult();
            return;
        }

        state.result = handlePlayer();
        if (state.result != Result.NotDecided) {
            addPoints();
            console.printResult();
            return;

        }

        state.result = handleDealer();
        if (state.result != Result.NotDecided) {
            addPoints();
            console.printResult();
            return;
        }

        state.roundEnded = true;
        state.result = checkState();
        addPoints();
        console.printResult();
    }

    Result start() {
        console.roundStart();
        Result result = checkState();
        if (result == Result.DealerWon) {
            handleDealer();
        }
        return result;
    }

    void addPoints() {
        Result result = checkState();
        if (result == Result.DealerWon) {
            dealerPoints++;
        } else {
            playerPoints++;
        }
    }

    Result handleDealer() {
        state.turn = Turn.DealerTurn;
        console.turnName();
        Card card = state.dealer.hand.getLast();
        console.openCard(card);
        console.printHands();
        while (checkCondition(state.dealer) == HandState.NotEnough &&
            state.dealer.getSum() < Dealer.DEALER_MAX) {
            card = state.deck.peekCard();
            state.dealer.addCard(card);
            console.openCard(card);
            console.printHands();

        }
        return checkState();
    }

    Result handlePlayer() {
        state.turn = Turn.PlayerTurn;
        console.turnName();
        while (checkCondition(state.player) == HandState.NotEnough && console.getInput()) {
            Card card = state.deck.peekCard();
            state.player.addCard(card);
            console.openCard(card);
            console.printHands();
        }
        state.turn = Turn.DealerTurn;
        return checkState();
    }

    Result checkState() {
        HandState player = checkCondition(state.player);
        HandState dealer = checkCondition(state.dealer);
        if (player == HandState.BlackJack && dealer != HandState.BlackJack) {
            return Result.PlayerWon;
        } else if (player != HandState.BlackJack && dealer == HandState.BlackJack) {
            return Result.DealerWon;
        } else if (player == HandState.OverHead) {
            return Result.DealerWon;
        } else if (dealer == HandState.OverHead) {
            return Result.PlayerWon;
        } else if (state.roundEnded) {
            if (state.player.getSum() > state.dealer.getSum()) {
                return Result.PlayerWon;
            } else if (state.player.getSum() < state.dealer.getSum()) {
                return Result.DealerWon;
            } else {
                return Result.Draw;
            }
        }
        return Result.NotDecided;
    }

    HandState checkCondition(Player player) {
        if (player.getSum() > 21) {
            return HandState.OverHead;
        } else if (player.getSum() == 21) {
            return HandState.BlackJack;
        } else {
            return HandState.NotEnough;
        }
    }
}