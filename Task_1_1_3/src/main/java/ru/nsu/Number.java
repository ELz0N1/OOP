package ru.nsu;

import java.util.HashMap;

/**
 * The Number class represents a constant.
 */
class Number extends Expression {

    private final int value;

    /**
     * Constructs a number expression.
     *
     * @param value value of number.
     */
    public Number(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public int evalHelper(HashMap<String, Integer> variables) {
        return value;
    }

    @Override
    protected boolean hasVariable() {
        return false;
    }

    @Override
    protected int safeEval() {
        return value;
    }

    @Override
    public Expression simplify() {
        return new Number(value);
    }
}