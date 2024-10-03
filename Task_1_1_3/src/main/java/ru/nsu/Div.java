package ru.nsu;

import java.util.HashMap;

/**
 * An division expression.
 */
class Div extends Expression {

    private final Expression left, right;

    /**
     * Constructs division expression.
     *
     * @param left  dividend.
     * @param right divisor.
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Div(
            new Sub(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))),
            new Mul(right, right)
        );
    }

    @Override
    public int evalHelper(HashMap<String, Integer> variables) {
        return left.evalHelper(variables) / right.evalHelper(variables);
    }
}