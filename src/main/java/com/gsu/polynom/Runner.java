package com.gsu.polynom;

public class Runner {
    public static void main(String[] args) {
        PolynomialZ2 p1 = new PolynomialZ2(1 , 0, 0, 0, 0, 0, 0 , 0, 1);
        PolynomialZ2 p2 = new PolynomialZ2(1 , 0, 1, 1);
        System.out.println(p1.isDivided(p2));


    }
}
