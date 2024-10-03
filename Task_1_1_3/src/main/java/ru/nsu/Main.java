package ru.nsu;

public class Main {

    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        int result = e.eval("x = 3; y = 4");
        Expression de = e.derivative("x");
        e.print();
        de.print();
        System.out.println(result);
    }
}
