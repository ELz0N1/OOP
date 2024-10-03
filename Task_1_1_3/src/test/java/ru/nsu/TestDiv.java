package ru.nsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDiv {

    @Test
    public void testToString() {
        Expression e = new Div(new Add(new Number(1), new Number(1)), new Number(3));
        Assertions.assertEquals(e.toString(), "((1+1)/3)");
    }

    @Test
    public void testDerivative() {
        Expression e = new Div(new Variable("x"), new Add(new Number(2),
            new Variable("x")));
        Expression de = e.derivative("x");
        Assertions.assertEquals(e.toString(), "(x/(2+x))");
        Assertions.assertEquals(de.toString(), "(((1*(2+x))-(x*(0+1)))/((2+x)*(2+x)))");
    }

    @Test
    public void testEval() {
        Expression e = new Div(new Add(new Number(2), new Variable("x")),
            new Variable("x"));
        int result = e.eval("x = 2; y = 13");
        Assertions.assertEquals(result, 2);
    }
}
