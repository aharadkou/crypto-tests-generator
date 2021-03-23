package com.gsu.testgenerator.impl;

import com.gsu.sequence.BitSequence;
import com.gsu.sequence.SequenceFiller;
import com.gsu.testgenerator.TestGenerator;

import java.math.BigInteger;
import java.util.*;

import static com.gsu.utils.MathUtils.isPowOf2;

public class TestGeneratorHamming implements TestGenerator {

    private static final int INFO_SYMBOLS_COUNT = 4;

    private static final int ANSWER_BIT_COUNT = 2;

    private static final int ANSWERS_COUNT = 4;

    private static final int CODE_DIMENSION = 2;

    private static final BitSequence zeroSequence = new BitSequence(new byte[]{0, 0, 0, 0});

    private final Set<BitSequence> answers;

    private final SequenceFiller sequenceFiller;

    private final List<BitSequence> buffer = new ArrayList<>(Collections.singletonList(zeroSequence));

    public TestGeneratorHamming(final SequenceFiller sequenceFiller) {
        this.sequenceFiller = sequenceFiller;
        answers = new LinkedHashSet<>();
        while (answers.size() != ANSWERS_COUNT) {
            answers.add(sequenceFiller.fillSequence(new BitSequence(ANSWER_BIT_COUNT), CODE_DIMENSION));
        }
    }

    @Override
    public String generateTestTask() {
        BitSequence infoSymbols = new BitSequence(INFO_SYMBOLS_COUNT);
        while (buffer.contains(infoSymbols)) {
            infoSymbols = sequenceFiller.fillSequence(infoSymbols, CODE_DIMENSION);
        }
        buffer.add(new BitSequence(infoSymbols.getBits()));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Даны 4 информационных символа -> ");
        stringBuilder.append(infoSymbols);
        insertHuffmanCheckingBits(infoSymbols);
        BitSequence rightAnswer = new BitSequence(
                new byte[] {infoSymbols.getBit(0), infoSymbols.getBit(1)});
        stringBuilder.append(".");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Найдите значения 1-ого и 2-ого проверочных разрядов кода Хэмминга(нумерация с 1).");
        stringBuilder.append(System.lineSeparator());
        int i = 0;
        for (BitSequence answer : answers) {
            if (answer.equals(rightAnswer)) {
                stringBuilder.append("+");
            }
            stringBuilder.append(i++);
            stringBuilder.append(") ");
            stringBuilder.append("1-ый разряд -> ");
            stringBuilder.append(answer.getBit(0));
            stringBuilder.append("; 2-ой разряд -> ");
            stringBuilder.append(answer.getBit(1));
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private void insertHuffmanCheckingBits(final BitSequence bitSequence) {
        for (int i = 0; i < bitSequence.getLength(); i++) {
            if (isPowOf2(i + 1)) {
                bitSequence.insertBit(i, (byte) 0);
            }
        }
        for (int i = 0; i < bitSequence.getLength(); i++) {
            if (isPowOf2(i + 1)) {
                int pow2 = (int)(Math.log10(i + 1) / Math.log10(2));
                int sumMod2 = 0;
                for (int j = i + 1; j < bitSequence.getLength(); j++) {
                    if (BigInteger.valueOf(j + 1).testBit(pow2)) {
                        sumMod2 = (sumMod2 + bitSequence.getBit(j)) % 2;
                    }
                }
                bitSequence.setBit(i, (byte) sumMod2);
            }
        }
    }

    @Override
    public void clearBuffer() {
        buffer.clear();
    }
}
