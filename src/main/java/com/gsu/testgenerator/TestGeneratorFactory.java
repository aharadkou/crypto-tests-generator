package com.gsu.testgenerator;

import com.gsu.sequence.RandomBitSequenceGenerator;
import com.gsu.sequence.SequenceFiller;
import com.gsu.testgenerator.impl.*;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TestGeneratorFactory {

    private TestGeneratorFactory() {

    }

    private static final SequenceFiller FILLER =
            new RandomBitSequenceGenerator();

    private static final Map<String, TestGenerator> GENERATORS =
            new LinkedHashMap<>();
    static {
        GENERATORS.put("1. Векторное подпространство", new TestGeneratorSubspace(FILLER));
        GENERATORS.put("2. Проверочные разряды Хэмминга", new TestGeneratorHamming(FILLER));
        GENERATORS.put("3. Радиус шара Хэмминга", new TestGeneratorHammingBall(FILLER));
        GENERATORS.put("4. Проверочная матрица в систематическом виде", new TestGeneratorCheckingMatrix(FILLER));
        GENERATORS.put("5. Порождающий многочлен циклического кода", new TestGeneratorCreationalPolynomial());
        GENERATORS.put("6. Параметры БЧХ-кода", new TestGeneratorCodeBCH());
    }

    public static boolean supports(final int taskNumber) {
        return taskNumber <= GENERATORS.size();
    }

    public static TestGenerator getTestGenerator(final int taskNumber) {
        return GENERATORS.get(getKeyByTaskNumber(taskNumber));
    }

    public static Set<String> getGeneratorDescriptions() {
        return new LinkedHashSet<>(GENERATORS.keySet());
    }

    public static Set<TestGenerator> getGenerators() {
        return new LinkedHashSet<>(GENERATORS.values());
    }

    private static String getKeyByTaskNumber(final int taskNumber) {
        return GENERATORS.keySet().stream()
                .filter(str -> str.startsWith(String.valueOf(taskNumber)))
                    .findFirst().get();
    }









}

