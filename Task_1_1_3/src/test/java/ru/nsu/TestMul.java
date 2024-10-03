package ru.nsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMul {

    @Test
    public void testToString() {
        Expression e = new Mul(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        Assertions.assertEquals(e.toString(), "(3*(2*x))");
    }

    @Test
    public void testDerivative1() {
        Expression e = new Mul(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        Expression de = e.derivative("x");
        Assertions.assertEquals(e.toString(), "(3*(2*x))");
        Assertions.assertEquals(de.toString(), "((0*(2*x))+(3*((0*x)+(2*1))))");
    }

    @Test
    public void testDerivative2() {
        Expression e = new Mul(new Number(3), new Sub(new Number(2),
            new Variable("x")));
        Expression de = e.derivative("x");
        Assertions.assertEquals(e.toString(), "(3*(2-x))");
        Assertions.assertEquals(de.toString(), "((0*(2-x))+(3*(0-1)))");
    }

    @Test
    public void testEval() {
        Expression e = new Mul(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        int result = e.eval("x = 10; y = 13");
        Assertions.assertEquals(result, 60);
    }
}
