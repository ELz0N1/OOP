package ru.nsu;

/**
 * Class of Value Error Exception.
 */
public class ValueErrorException extends RuntimeException {

    /**
     * Construct new exception.
     *
     * @param message error message.
     */
    public ValueErrorException(String message) {
        super(message);
    }
}
