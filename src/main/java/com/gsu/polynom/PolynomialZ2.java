package com.gsu.polynom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Represents an polynomial.
 * Operations don't change initial object state.
 */
public class PolynomialZ2 {

    private final List<Integer> polynomial;

    private static final int FIELD_DIMENSION = 2;

    public PolynomialZ2(final int... members) {
        polynomial = new ArrayList<>(
                Arrays.asList(IntStream.of(members).boxed().toArray(Integer[]::new))
        );
    }

    public PolynomialZ2(final PolynomialZ2 other) {
        polynomial = new ArrayList<>(other.polynomial);
    }

    /**
     * Returns the result of subtraction of two polynomials
     */
    public PolynomialZ2 subtract(final PolynomialZ2 other) {
        PolynomialZ2 minSizePolynomial;
        PolynomialZ2 maxSizePolynomial;
        if (polynomial.size() >= other.polynomial.size()) {
            maxSizePolynomial = this;
            minSizePolynomial = other;
        } else {
            minSizePolynomial = this;
            maxSizePolynomial = other;
        }
        PolynomialZ2 newPolynomial = new PolynomialZ2(maxSizePolynomial);
        for (int i = 0; i < minSizePolynomial.polynomial.size(); i++) {
            newPolynomial.polynomial.set(i,
                    Math.abs(polynomial.get(i) - other.polynomial.get(i) % FIELD_DIMENSION));
        }
        newPolynomial.normalize();
        return newPolynomial;
    }

    /**
     * Returns the result of multiplication of two polynomials
     */
    public PolynomialZ2 multiply(final PolynomialZ2 other) {
        PolynomialZ2 result = new PolynomialZ2();
        for (int i = 0; i < polynomial.size(); i++) {
            for (int j = 0; j < other.polynomial.size(); j++) {
                    if (polynomial.get(i) != 0 && other.polynomial.get(j) != 0) {
                        result.setMember(i + j);
                    }
            }
        }
        return result;
    }

    /**
     * Returns true if this is divided by other
     */
    public boolean isDivided(final PolynomialZ2 other) {
        PolynomialZ2 rest = this;
        do {
            PolynomialZ2 theBiggestDiv = rest.divideTheBiggest(other);
            PolynomialZ2 theBiggestMul = theBiggestDiv.multiply(other);
            rest = rest.subtract(theBiggestMul);
        } while (rest.polynomial.size() >= other.polynomial.size());
        return rest.polynomial.size() == 0;
    }


    /**
     * @return polynomial degree
     */
    public int getDegree() {
        return polynomial.size();
    }

    private PolynomialZ2 divideTheBiggest(final PolynomialZ2 other) {
        PolynomialZ2 result = new PolynomialZ2();
        result.setMember(polynomial.size() - other.polynomial.size());
        return result;
    }

    private void normalize() {
        int i = polynomial.size() - 1;
        while (i >= 0 && polynomial.get(i) == 0) {
            polynomial.remove(i);
            i--;
        }
    }

    private void setMember(int index) {
        if (polynomial.size() - 1 >= index) {
            polynomial.set(index, (polynomial.get(index) + 1) % FIELD_DIMENSION);
        } else {
            int size = polynomial.size();
            for (int i = 0; i < index - size; i++) {
                polynomial.add(0);
            }
            polynomial.add(1);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < polynomial.size(); i++) {
            if (polynomial.get(i) == 1) {
                if (i == 0) {
                    result.append(1);
                } else {
                    result.append("x^").append(i);
                }
                if (i != polynomial.size() - 1) {
                    result.append(" + ");
                }
            }
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PolynomialZ2)) return false;
        PolynomialZ2 that = (PolynomialZ2) o;
        return toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(polynomial.hashCode());
    }
}
