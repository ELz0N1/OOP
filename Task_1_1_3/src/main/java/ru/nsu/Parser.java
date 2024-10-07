package ru.nsu;

import java.util.Stack;

/**
 * Parser implementation.
 */
public class Parser {

    /**
     * Checks if string is decimal.
     *
     * @param str string for checking.
     * @return true if all characters in string is decimal.
     */
    private static boolean isDecimal(char[] str) {
        for (char ch : str) {
            if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Return parsed subexpression.
     *
     * @param str   string for parsing;
     * @param start start index;
     * @param end   end index;
     * @return parsed expression.
     * @throws IncorrectExpressionException if string with expression is incorrect.
     */
    private static Expression subExpression(char[] str, int start, int end)
        throws IncorrectExpressionException {
        return parse(String.valueOf(str).substring(start, end));
    }

    /**
     * Gets priority of mathematical operation.
     *
     * @param ch mathematical operation.
     * @return priority of given operation.
     */
    private static int getPriority(char ch) {
        if (ch == '+' || ch == '-') {
            return 0;
        } else if (ch == '*' || ch == '/') {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Gets substring of string on given interval.
     *
     * @param str   given string.
     * @param start start index.
     * @param end   end index.
     * @return substring of string on given interval.
     */
    private static char[] getSubstring(char[] str, int start, int end) {
        char[] subStr = new char[end - start];
        if (end - start >= 0) {
            System.arraycopy(str, start, subStr, 0, end - start);
        }
        return subStr;
    }

    /**
     * Parses string with expression.
     *
     * @param str string for parsing;
     * @return parsed expression.
     * @throws IncorrectExpressionException if string with expression is incorrect.
     */
    public static Expression parse(String str) throws IncorrectExpressionException {
        if (str.isEmpty()) {
            return null;
        }
        char[] chars = str.toCharArray();
        if (chars[0] == '(' && chars[chars.length - 1] == ')') {
            chars = getSubstring(chars, 1, chars.length - 1);
        }
        Stack<Integer> stack = new Stack<>();
        int[] priorityIndex = {-1, -1};
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case '(':
                    stack.push(i);
                    break;
                case ')':
                    if (stack.empty()) {
                        throw new IncorrectExpressionException(
                            "There is no opened bracket before closed one!");
                    }
                    stack.pop();
                    break;
                case '+':
                    if (stack.empty()) {
                        return new Add(subExpression(chars, 0, i),
                            subExpression(chars, i + 1, chars.length));
                    }
                    break;
                case '-':
                    if (stack.empty()) {
                        return new Sub(subExpression(chars, 0, i),
                            subExpression(chars, i + 1, chars.length));
                    }
                    break;
                case '*':
                    if (stack.empty()) {
                        int priority = getPriority('*');
                        if (priorityIndex[priority] == -1) {
                            priorityIndex[priority] = i;
                        }
                    }
                    break;
                case '/':
                    if (stack.empty()) {
                        int priority = getPriority('/');
                        if (priorityIndex[priority] == -1) {
                            priorityIndex[priority] = i;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        if (!stack.empty()) {
            throw new IncorrectExpressionException("Left bracket never closed!");
        }
        int index = priorityIndex[getPriority('*')];
        if (index != -1) {
            if (chars[index] == '*') {
                return new Mul(subExpression(chars, 0, index),
                    subExpression(chars, index + 1, chars.length));
            } else if (chars[index] == '/') {
                return new Div(subExpression(chars, 0, index),
                    subExpression(chars, index + 1, chars.length));
            }
        }
        if (isDecimal(chars)) {
            return new Number(Integer.parseInt(String.valueOf(chars)));
        } else {
            return new Variable(String.valueOf(chars));
        }
    }
}
