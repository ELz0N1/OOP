package ru.nsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class for testing Parser.
 */
public class TestParser {

    private void parsing(String str, String ans) throws IncorrectExpressionException {
        Expression ex = Parser.parse(str);
        Assertions.assertNotNull(ex);
        Assertions.assertEquals(ans, ex.toString());
    }

    private void correctTest(String str, String ans) {
        try {
            parsing(str, ans);
        } catch (IncorrectExpressionException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testEmptyParse() {
        try {
            Expression ex = Parser.parse("");
            Assertions.assertNull(ex);
        } catch (IncorrectExpressionException e) {
            Assertions.fail();
        }

    }

    @Test
    public void testCorrectParse1() {
        String str = "(0+((0*x)+(2*1)))";
        correctTest(str, str);
    }

    @Test
    public void testCorrectParse2() {
        String str = "(2*((4-(1+4))/(5+(2*3))))";
        correctTest(str, str);
    }

    @Test
    public void testCorrectParse3() {
        String str = "1+2+3+4/2";
        String ans = "(1+(2+(3+(4/2))))";
        correctTest(str, ans);
    }

    @Test
    public void testCorrectParse4() {
        String str = "1*3-(1+4/2)";
        String ans = "((1*3)-(1+(4/2)))";
        correctTest(str, ans);
    }

    @Test
    public void correctParseNumberTest() {
        String str = "1024";
        correctTest(str, str);
    }

    @Test
    public void correctParseVariableTest() {
        String str = "x1";
        correctTest(str, str);
    }

    @Test
    public void testIncorrectParse() {
        String str = "(2*((4-(1+4))/(5+(2*3)))";
        Assertions.assertThrows(IncorrectExpressionException.class, () -> parsing(str, str));
    }
}