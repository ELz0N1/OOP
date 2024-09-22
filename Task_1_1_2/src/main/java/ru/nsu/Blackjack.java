package ru.nsu;

import java.util.Scanner;


/**
 * Variants of hand state.
 */
enum HandState {
    BlackJack,
    OverHead,
    NotEnough,
}

/**
 * Variants of turns in game.
 */
enum Turn {
    DealerTurn,
    PlayerTurn,
}

/**
 * Player's input values.
 */
enum PlayerInput {
    Continue(1),
    Stop(0);
    final int value;

    PlayerInput(int value) {
        this.value = value;
    }
}

/**
 * Variants of round result.
 */
enum Result {
    NotDecided,
    PlayerWon,
    DealerWon,
    Draw,
}

/**
 * Blackjack game class implementation.
 */
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

    private class GameIo {

        Scanner in;
        private static final String askForInput =
            "Введите \"1\", чтобы взять карту, и \"0\", чтобы остановиться...";

        GameIo() {
            in = new Scanner(System.in);
        }

        /**
         * Gets player's input value.
         *
         * @return boolean value to continue or stop player's turn.
         * @throws InvalidInputException if input value mismatch the given options.
         */
        boolean getInput() {
            Scanner scanner = new Scanner(System.in);
            System.out.println(askForInput);

            while (true) {
                try {
                    int input = scanner.nextInt();
                    if (input == PlayerInput.Continue.value) {
                        return true;
                    } else if (input == PlayerInput.Stop.value) {
                        return false;
                    }
                    if (input != 0 && input != 1) {
                        throw new InvalidInputException("Неправильный ввод, повторите ещё раз: ");
                    }
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());

                }
            }
        }

        /**
         * Prints current turn.
         */
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

        /**
         * Prints opened card.
         *
         * @param card Card that opened.
         */
        void openCard(Card card) {
            String message = "";
            if (state.turn == Turn.PlayerTurn) {
                message += "Вы открыли карту " + card.toString();
            } else {
                message += "Дилер открывает ";
                if (card.isHidden()) {
                    message += "закрытую ";
                }
                message += "карту " + card.toString();
            }
            System.out.print(message + "\n");
        }

        /**
         * Prints player's and dealer's hands.
         */
        void printHands() {
            String message = "  Ваши карты: " + state.player.toString()
                + " ⇒ " + state.player.getSum() + "\n";
            message += "  Карты дилера: " + state.dealer.toString();
            if (state.turn == Turn.DealerTurn) {
                message += " ⇒ " + state.dealer.getSum();
            }
            System.out.print(message + "\n\n");
        }

        /**
         * Prints start round information.
         */
        void roundStart() {
            String string = String.format("Раунд %d\nДилер раздал карты", round);
            System.out.println(string);
            printHands();
        }

        /**
         * Prints result of round.
         */
        void printResult() {
            String message = "";
            if (state.result == Result.DealerWon) {
                message += "Дилер выйграл раунд! ";
            } else if (state.result == Result.PlayerWon) {
                message += "Вы выйграли раунд! ";
            } else {
                message += "Ничья! ";
            }
            message += " Счёт " + playerPoints + ":"
                + dealerPoints + " ";
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
    GameIo console;

    Blackjack() {
        console = new GameIo();
        playerPoints = 0;
        dealerPoints = 0;
        round = 1;
    }

    /**
     * Method to run game.
     */
    void run() {
        System.out.println("Добро пожаловать в блэкджек!");
        while (true) {
            gameloop();
            round += 1;
        }
    }

    /**
     * Main game loop.
     */
    void gameloop() {
        state = new GameState();
        state.roundEnded = false;

        console.roundStart();

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


    /**
     * Adds points to winner of round.
     */
    void addPoints() {
        Result result = checkState();
        if (result == Result.DealerWon) {
            dealerPoints++;
        } else {
            playerPoints++;
        }
    }

    /**
     * Dealer's turn.
     *
     * @return result of dealer's turn.
     */
    Result handleDealer() {
        state.turn = Turn.DealerTurn;
        console.turnName();
        Card card = state.dealer.hand.getLast();
        state.dealer.openCard();
        console.openCard(card);
        console.printHands();
        while (checkCondition(state.dealer) == HandState.NotEnough
            && state.dealer.getSum() < Dealer.MAX) {
            card = state.deck.pickCard();
            state.dealer.addCard(card);
            console.openCard(card);
            console.printHands();
        }
        return checkState();
    }

    /**
     * Player's turn.
     *
     * @return result of player's turn.
     */
    Result handlePlayer() {
        state.turn = Turn.PlayerTurn;
        console.turnName();
        while (checkCondition(state.player) == HandState.NotEnough && console.getInput()) {
            Card card = state.deck.pickCard();
            state.player.addCard(card);
            console.openCard(card);
            console.printHands();
        }
        state.turn = Turn.DealerTurn;
        return checkState();
    }

    /**
     * Method to check state of game.
     *
     * @return round results.
     */
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

    /**
     * Method to check hand condition of player.
     *
     * @param player Player to check hand condition.
     * @return Current player's hand condition.
     */
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