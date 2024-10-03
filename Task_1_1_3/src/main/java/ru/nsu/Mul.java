package ru.nsu;

import java.util.HashMap;


/**
 * An multiplication expression.
 */
public class Mul extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Constructs multiplication expression.
     *
     * @param left  left operand.
     * @param right right operand.
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left.derivative(variable), right),
            new Mul(left, right.derivative(variable)));
    }

    @Override
    public int evalHelper(HashMap<String, Integer> variables) {
        return left.evalHelper(variables) * right.evalHelper(variables);
    }
}