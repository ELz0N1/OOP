package ru.nsu;

/**
 * Exception for invalid input values.
 */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
