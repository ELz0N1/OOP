package ru.nsu;

/**
 * Class of Variable Evaluation Exception.
 */
public class VariableEvalException extends RuntimeException {

    /**
     * Construct new exception.
     *
     * @param message error message.
     */
    public VariableEvalException(String message) {
        super(message);
    }
}
