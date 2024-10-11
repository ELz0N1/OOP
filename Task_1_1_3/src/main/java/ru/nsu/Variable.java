package ru.nsu;

import java.util.HashMap;

/**
 * The Variable class represents a variable.
 */
public class Variable extends Expression {

    private final String name;

    /**
     * Constructs a variable expression.
     *
     * @param name name of variable.
     */
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);
    }

    @Override
    public int evalHelper(HashMap<String, Integer> variables) throws ValueErrorException {
        if (variables == null || !variables.containsKey(name)) {
            throw new ValueErrorException("Variable " + name + " has no value!");
        }
        return variables.get(name);
    }

    @Override
    protected boolean hasVariable() {
        return true;
    }

    @Override
    protected int simplifyEval() throws VariableEvalException {
        throw new VariableEvalException("Can't evaluate variable" + name);
    }

    @Override
    public Expression simplify() {
        return new Variable(name);
    }
}