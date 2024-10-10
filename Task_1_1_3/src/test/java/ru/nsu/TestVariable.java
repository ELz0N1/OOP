package ru.nsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestVariable {

    @Test
    public void testToString() {
        Expression e = new Variable("x");
        Assertions.assertEquals(e.toString(), "x");
    }

    @Test
    public void testDerivative1() {
        Expression e = new Variable("x");
        Expression de = e.derivative("x");
        Assertions.assertEquals(e.toString(), "x");
        Assertions.assertEquals(de.toString(), "1");
    }

    @Test
    public void testDerivative2() {
        Expression e = new Variable("x");
        Expression de = e.derivative("y");
        Assertions.assertEquals(e.toString(), "x");
        Assertions.assertEquals(de.toString(), "0");
    }

    @Test
    public void testEval() {
        Expression e = new Variable("y");
        int result = e.eval("y = 90");
        Assertions.assertEquals(result, 90);
    }

    @Test
    public void testSimplify() {
        Expression e = new Variable("y");
        Assertions.assertEquals(e.simplify().toString(), "y");
    }
}
