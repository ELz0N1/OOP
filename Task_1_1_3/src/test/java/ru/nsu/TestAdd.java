package ru.nsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAdd {

    @Test
    public void testToString() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        Assertions.assertEquals(e.toString(), "(3+(2*x))");
    }

    @Test
    public void testDerivative1() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        Expression de = e.derivative("x");
        Assertions.assertEquals(e.toString(), "(3+(2*x))");
        Assertions.assertEquals(de.toString(), "(0+((0*x)+(2*1)))");
    }

    @Test
    public void testDerivative2() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        Expression de = e.derivative("y");
        Assertions.assertEquals(e.toString(), "(3+(2*x))");
        Assertions.assertEquals(de.toString(), "(0+((0*x)+(2*0)))");
    }

    @Test
    public void testEval() {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
            new Variable("x")));
        int result = e.eval("x = 10; y = 13");
        Assertions.assertEquals(result, 23);
    }

    @Test
    public void testSimplify1() {
        Expression e = new Add(new Add(new Variable("x"), new Add(new Number(5),
            new Number(4))), new Number(0));
        Assertions.assertEquals(e.simplify().toString(), "(x+9)");
    }

    @Test
    public void testSimplify2() {
        Expression e = new Add(new Number(0), new Add(new Variable("x"),
            new Add(new Number(4), new Number(5))));
        Assertions.assertEquals(e.simplify().toString(), "(x+9)");
    }

    @Test
    public void testSimplify3() {
        Expression e = new Add(new Number(0), new Add(new Number(9),
            new Add(new Number(4), new Number(5))));
        Assertions.assertEquals(e.simplify().toString(), "18");
    }

    @Test
    public void testSimplify4() {
        Expression e = new Add(new Number(2),
            new Add(new Number(1), new Mul(new Number(0), new Variable("x"))));
        Assertions.assertEquals(e.simplify().toString(), "3");
    }

    @Test
    public void testSimplify5() {
        Expression e = new Add(new Add(new Mul(new Variable("y"), new Number(0)), new Number(2)),
            new Add(new Number(1), new Mul(new Number(0), new Variable("x"))));
        Assertions.assertEquals(e.simplify().toString(), "3");
    }
}
