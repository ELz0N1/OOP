package ru.nsu;

import java.util.HashMap;

/**
 * A mathematical expression.
 */
public abstract class Expression {

    @Override
    public abstract String toString();

    /**
     * Print this expression to standard output.
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * Calculates the derivative of an expression.
     *
     * @param variable the variable by which the derivative is to be calculated.
     * @return derivative of this expression.
     */
    public abstract Expression derivative(String variable);

    /**
     * Implements of {@link Expression#eval} method.
     *
     * @param variables hashMap of variables with theirs values.
     * @return result of expression.
     */
    protected abstract int evalHelper(HashMap<String, Integer> variables);

    /**
     * Evaluates expression.
     *
     * @param variables designation of variables using {@code ;}.
     * @return result of expression.
     */
    public int eval(String variables) {
        HashMap<String, Integer> dictVars = getVariables(variables);
        return evalHelper(dictVars);
    }

    /**
     * Parses a string with variable assignments and returns a map of variables and their values.
     *
     * @param variableAssignments given string to parse.
     * @return a map of variables and their values.
     */
    private static HashMap<String, Integer> getVariables(String variableAssignments) {
        HashMap<String, Integer> variableValues = new HashMap<>();
        String[] assignments = variableAssignments.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            String variable = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            variableValues.put(variable, value);
        }
        return variableValues;
    }

    /**
     * Checks do the expression has variable.
     *
     * @return returns true if expression contains variable otherwise false.
     */
    protected abstract boolean hasVariable();

    /**
     * Evaluates expression to simplify.
     *
     * @return result of expression.
     */
    protected abstract int simplifyEval();

    /**
     * Simplifies the expression.
     *
     * @return simplified expression.
     */
    public abstract Expression simplify();
}