package ru.nsu;

import java.util.HashMap;

/**
 * An division expression.
 */
class Div extends Expression {

    private final Expression left;
    private final Expression right;


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
        if (right.evalHelper(variables) == 0) {
            throw new ArithmeticException("Division by zero!");
        }
        return left.evalHelper(variables) / right.evalHelper(variables);
    }

    @Override
    protected boolean hasVariable() {
        return right.hasVariable() || left.hasVariable();
    }

    @Override
    protected int simplifyEval() {
        return left.simplifyEval() / right.simplifyEval();
    }

    @Override
    public Expression simplify() {
        if (!hasVariable()) {
            return new Number(simplifyEval());
        }
        Expression newLeft = left.simplify();
        Expression newRight = right.simplify();

        if (!newLeft.hasVariable() && !newRight.hasVariable()) {
            return new Number(newLeft.simplifyEval() / newRight.simplifyEval());
        }

        if (!newRight.hasVariable()) {
            int rightResult = newRight.simplifyEval();
            if (rightResult == 1) {
                return newLeft;
            }
        }
        if (!newLeft.hasVariable()) {
            int leftResult = newLeft.simplifyEval();
            if (leftResult == 0) {
                return new Number(0);
            }
        }
        return new Div(newLeft, newRight);
    }
}