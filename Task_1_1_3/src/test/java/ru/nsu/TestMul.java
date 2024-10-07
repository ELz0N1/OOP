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

    @Test
    public void testSimplify1() {
        Expression e = new Mul(new Sub(new Number(2), new Number(1)), new Number(8));
        Assertions.assertEquals(e.simplify().toString(), "8");
    }

    @Test
    public void testSimplify2() {
        Expression e = new Mul(new Mul(new Variable("x"), new Number(1)), new Number(2));
        Assertions.assertEquals(e.simplify().toString(), "(x*2)");
    }

    @Test
    public void testSimplify3() {
        Expression e = new Mul(new Mul(new Number(0), new Variable("x")), new Number(2));
        Assertions.assertEquals(e.simplify().toString(), "0");
    }

    @Test
    public void testSimplify4() {
        Expression e = new Mul(new Mul(new Number(1), new Number(1)),
            new Mul(new Number(4), new Variable("y")));
        Assertions.assertEquals(e.simplify().toString(), "(4*y)");
    }
}
