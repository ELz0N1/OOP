package ru.nsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSub {

    @Test
    public void testToString() {
        Expression e = new Sub(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        Assertions.assertEquals(e.toString(), "(3-(2*x))");
    }

    @Test
    public void testDerivative1() {
        Expression e = new Sub(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        Expression de = e.derivative("x");
        Assertions.assertEquals(e.toString(), "(3-(2*x))");
        Assertions.assertEquals(de.toString(), "(0-((0*x)+(2*1)))");
    }

    @Test
    public void testDerivative2() {
        Expression e = new Sub(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        Expression de = e.derivative("y");
        Assertions.assertEquals(e.toString(), "(3-(2*x))");
        Assertions.assertEquals(de.toString(), "(0-((0*x)+(2*0)))");
    }

    @Test
    public void testDerivative3() {
        Expression e = new Sub(new Number(3), new Sub(new Number(2),
            new Variable("x")));
        Expression de = e.derivative("x");
        Assertions.assertEquals(e.toString(), "(3-(2-x))");
        Assertions.assertEquals(de.toString(), "(0-(0-1))");
    }

    @Test
    public void testEval() {
        Expression e = new Sub(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        int result = e.eval("x = 10; y = 13");
        Assertions.assertEquals(result, -17);
    }

    @Test
    public void testSimplify1() {
        Expression e = new Sub(new Sub(new Add(new Number(1), new Number(1)), new Variable("x")),
            new Sub(new Number(2), new Variable("x")));
        Assertions.assertEquals(e.simplify().toString(), "0");
    }

    @Test
    public void testSimplify2() {
        Expression e = new Sub(new Sub(new Variable("y"),
            new Add(new Number(1), new Number(2))), new Number(0));
        Assertions.assertEquals(e.simplify().toString(), "(y-3)");
    }

    @Test
    public void testSimplify3() {
        Expression e = new Sub(new Number(0),
            new Sub(new Variable("x"), new Sub(new Number(3), new Number(2))));
        Assertions.assertEquals(e.simplify().toString(), "(x-1)");
    }
}
