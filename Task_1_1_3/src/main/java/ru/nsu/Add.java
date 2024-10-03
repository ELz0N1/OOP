package ru.nsu;

import java.util.HashMap;

/**
 * An addition expression.
 */
public class Add extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Constructs addition expression.
     *
     * @param left  left operand.
     * @param right right operand.
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable),
            right.derivative(variable));
    }

    @Override
    public int evalHelper(HashMap<String, Integer> variables) {
        return left.evalHelper(variables)
            + right.evalHelper(variables);
    }
}