package ru.nsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestNumber {

    @Test
    public void testToString() {
        Expression e = new Number(13);
        Assertions.assertEquals(e.toString(), "13");
    }

    @Test
    public void testDerivative() {
        Expression e = new Number(18);
        Expression de = e.derivative("x");
        Assertions.assertEquals(e.toString(), "18");
        Assertions.assertEquals(de.toString(), "0");
    }

    @Test
    public void testEval() {
        Expression e = new Number(52);
        int result = e.eval("x = 99");
        Assertions.assertEquals(result, 52);
    }
}
