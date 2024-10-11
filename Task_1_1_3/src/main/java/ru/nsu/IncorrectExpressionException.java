package ru.nsu;

/**
 * Class of Incorrect Expression Exception.
 */
public class IncorrectExpressionException extends Exception {

    /**
     * Construct new exception.
     *
     * @param message error message.
     */
    public IncorrectExpressionException(String message) {
        super(message);
    }
}