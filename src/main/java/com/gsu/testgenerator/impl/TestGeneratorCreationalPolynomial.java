package com.gsu.testgenerator.impl;

import com.gsu.polynom.PolynomialZ2;
import com.gsu.testgenerator.TestGenerator;
import com.gsu.utils.MathUtils;

import java.util.HashSet;
import java.util.Set;


public class TestGeneratorCreationalPolynomial implements TestGenerator {

    private final int POLYNOMIAL_DEGREE_MIN = 2;

    private final int POLYNOMIAL_DEGREE_MAX = 5;

    private final int ANSWERS_COUNT = 5;

    private final Set<PolynomialZ2> buffer = new HashSet<>();

    @Override
    public String generateTestTask() {
        StringBuilder test = new StringBuilder();
        final int polynomialDegree = MathUtils.nextIntInclusive(POLYNOMIAL_DEGREE_MIN, POLYNOMIAL_DEGREE_MAX);
        PolynomialZ2 polynomial;
        do {
            polynomial = generatePolynomial(polynomialDegree);
        } while (buffer.contains(polynomial) && buffer.size() <= 5);
        buffer.add(polynomial);
        boolean isAnswered = false;
        test.append("Дан многочлен над полем GF(2) -> ").append(polynomial).append(".");
        test.append(System.lineSeparator()).append("При каком наименьшем n он является порождающим многочленом ");
        test.append(System.lineSeparator()).append( "некоторого циклического кода?").append(System.lineSeparator());
        int answerNum = 0;
        for (int i = polynomialDegree + 1; i < polynomialDegree + ANSWERS_COUNT + 1; i++) {
            int[] basePolynomialArray = new int[i + 1];
            basePolynomialArray[0] = 1;
            basePolynomialArray[basePolynomialArray.length - 1] = 1;
            PolynomialZ2 basePolynomial = new PolynomialZ2(basePolynomialArray);
            if ((basePolynomial.isDivided(polynomial) && !isAnswered) ||
                    (i == polynomialDegree + ANSWERS_COUNT && !isAnswered)) {
                test.append("+");
                isAnswered = true;
            }
            test.append(answerNum++).append(") ");
            if (i == polynomialDegree + ANSWERS_COUNT) {
                test.append("Такого n не представлено");
            } else {
                test.append(i);
            }
            test.append(System.lineSeparator());
        }
        return test.toString();
    }

    private PolynomialZ2 generatePolynomial(final int polynomialDegree) {
        int[] polynomialArray = new int[polynomialDegree + 1];
        for (int i = 0; i < polynomialArray.length; i++) {
            polynomialArray[i] = MathUtils.nextIntInclusive(0, 1);
        }
        polynomialArray[polynomialArray.length - 1] = 1;
        return new PolynomialZ2(polynomialArray);
    }

    @Override
    public void clearBuffer() {
        buffer.clear();
    }
}
