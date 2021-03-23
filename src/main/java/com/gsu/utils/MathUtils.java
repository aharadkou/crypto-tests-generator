package com.gsu.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {

    private MathUtils() {

    }

    public static int nextIntInclusive(final int lowerBound, final int topBound) {
        final Random random = ThreadLocalRandom.current();
        return random.nextInt(topBound - lowerBound + 1) + lowerBound;
    }

    public static int factorial(final int number) {
        int factorial = 1;
        for (int i = 1; i <= number; ++i) {
            factorial *= i;
        }
        return factorial;
    }

    public static boolean isPowOf2(final int number) {
        return (number & (number - 1)) == 0;
    }

}
