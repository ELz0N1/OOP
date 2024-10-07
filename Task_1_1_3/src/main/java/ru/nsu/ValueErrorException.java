package ru.nsu;

/**
 * Class of Value Error Exception.
 */
public class ValueErrorException extends Exception {

    /**
     * Construct new exception.
     *
     * @param message error message.
     */
    public ValueErrorException(String message) {
        super(message);
    }
}
