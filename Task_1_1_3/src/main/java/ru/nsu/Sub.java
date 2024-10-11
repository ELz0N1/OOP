package ru.nsu;

import java.util.HashMap;
import java.util.Objects;

/**
 * An subtraction expression.
 */
public class Sub extends Expression {

    private final Expression left;
    private final Expression right;

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

    @Override
    protected boolean hasVariable() {
        return right.hasVariable() || left.hasVariable();
    }

    @Override
    protected int simplifyEval() {
        return left.simplifyEval() - right.simplifyEval();
    }

    @Override
    public Expression simplify() {
        if (!hasVariable()) {
            return new Number(simplifyEval());
        }
        Expression newLeft = left.simplify();
        Expression newRight = right.simplify();

        if (!newLeft.hasVariable() && !newRight.hasVariable()) {
            return new Number(newLeft.simplifyEval() - newRight.simplifyEval());
        }

        if (!newLeft.hasVariable()) {
            int leftResult = newLeft.simplifyEval();
            if (leftResult == 0) {
                return newRight;
            }
        }
        if (!newRight.hasVariable()) {
            int rightResult = newRight.simplifyEval();
            if (rightResult == 0) {
                return newLeft;
            }
        }
        if (Objects.equals(newLeft.toString(), newRight.toString())) {
            return new Number(0);
        }
        return new Sub(newLeft, newRight);
    }
}