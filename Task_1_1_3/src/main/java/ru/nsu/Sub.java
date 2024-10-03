package ru.nsu;

import java.util.HashMap;

/**
 * An subtraction expression.
 */
public class Sub extends Expression {

    private final Expression left, right;

    /**
     * Constructs subtraction expression.
     *
     * @param left  left operand
     * @param right right operand
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int evalHelper(HashMap<String, Integer> variables) {
        return left.evalHelper(variables) - right.evalHelper(variables);
    }
}