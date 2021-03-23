package com.gsu.testgenerator.impl;

import com.gsu.sequence.BitSequence;
import com.gsu.sequence.SequenceFiller;
import com.gsu.testgenerator.TestGenerator;

import static com.gsu.utils.MathUtils.*;

public class TestGeneratorHammingBall implements TestGenerator {

    private static final int SEQUENCE_LENGTH_MIN = 4;

    private static final int SEQUENCE_LENGTH_MAX = 9;

    private static final int BALL_RADIUS_MAX = 2;

    private static final int BALL_RADIUS_MIN = 1;

    private static final int CODE_DIMENSION = 3;

    private final SequenceFiller sequenceFiller;

    public TestGeneratorHammingBall(final SequenceFiller sequenceFiller) {
        this.sequenceFiller = sequenceFiller;
    }

    @Override
    public String generateTestTask() {
        final int sequenceLength = nextIntInclusive(SEQUENCE_LENGTH_MIN, SEQUENCE_LENGTH_MAX);
        final int radius = nextIntInclusive(BALL_RADIUS_MIN, BALL_RADIUS_MAX);
        BitSequence sequence = new BitSequence(sequenceLength);
        sequenceFiller.fillSequence(sequence, CODE_DIMENSION);
        final int ballElementsCount = getBallElementsCount(sequenceLength, radius);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Дана троичная последовательность ");
        stringBuilder.append(sequence);
        stringBuilder.append(". ");
        stringBuilder.append("Радиус шара равен ");
        stringBuilder.append(radius);
        stringBuilder.append(". ");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Найти число элементов в шаре.");
        stringBuilder.append(System.lineSeparator());
        int answerNumber = 0;
        for (int i = ballElementsCount - 2; i <= ballElementsCount + 2; i++) {
            if (i == ballElementsCount) {
                stringBuilder.append("+");
            }
            stringBuilder.append(answerNumber++);
            stringBuilder.append(") ");
            stringBuilder.append(i);
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private int getBallElementsCount(final int length, final int radius) {
        int elementsCount = 0;
        for (int i = 0; i <= radius; i++) {
            int cFromLengthByI = factorial(length) / (factorial(i) * factorial(length - i));
            elementsCount += cFromLengthByI * (int) Math.pow(CODE_DIMENSION - 1, i);
        }
        return elementsCount;
    }

}
