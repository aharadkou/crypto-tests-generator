package com.gsu.testgenerator.impl;

import com.gsu.sequence.BitSequence;
import com.gsu.sequence.SequenceFiller;
import com.gsu.testgenerator.TestGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gsu.utils.MathUtils.nextIntInclusive;

public class TestGeneratorSubspace implements TestGenerator {

    private static final int ANSWERS_COUNT = 5;

    private static final int SEQUENCE_ELEMENTS_COUNT = 3;

    private static final int SEQUENCE_BITS_COUNT = 4;

    private static final int CODE_DIMENSION = 2;

    private final SequenceFiller sequenceFiller;

    public TestGeneratorSubspace(final SequenceFiller sequenceFiller) {
        this.sequenceFiller = sequenceFiller;
    }

    @Override
    public String generateTestTask() {
        List<BitSequence> sequences = new ArrayList<>(SEQUENCE_ELEMENTS_COUNT);
        sequences.add(new BitSequence(SEQUENCE_BITS_COUNT));
        while (sequences.size() != SEQUENCE_ELEMENTS_COUNT) {
            BitSequence randomSequence = sequenceFiller.fillSequence(
                    new BitSequence(SEQUENCE_BITS_COUNT), CODE_DIMENSION);
            if (!sequences.contains(randomSequence)) {
                sequences.add(randomSequence);
            }
        }

        List<BitSequence> answers = new ArrayList<>(ANSWERS_COUNT);
        while(answers.size() != ANSWERS_COUNT - 1) {
            BitSequence randomSequence = sequenceFiller.fillSequence(
                    new BitSequence(SEQUENCE_BITS_COUNT), CODE_DIMENSION);
            if (!answers.contains(randomSequence) && !sequences.contains(randomSequence)) {
                answers.add(randomSequence);
            }
        }
        BitSequence elementToAdd = getElementToAdd(sequences);
        int rightAnswerNumber;
        if (elementToAdd == null) {
            rightAnswerNumber = ANSWERS_COUNT - 1;
        } else {
            if(answers.contains(elementToAdd)) {
                rightAnswerNumber = answers.indexOf(elementToAdd);
            } else {
                rightAnswerNumber = nextIntInclusive(0, ANSWERS_COUNT - 2);
                answers.set(rightAnswerNumber, elementToAdd);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Даны 3 последовательности: ");
        stringBuilder.append(sequences.stream().map(Object::toString).collect(Collectors.joining(", ")));
        stringBuilder.append(".");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Какую последовательность необходимо к ним добавить, чтобы ");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("полученные 4 последовательности образовывали векторное подпространство?");
        stringBuilder.append(System.lineSeparator());
        for (int i = 0; i < ANSWERS_COUNT; i++) {
            if (i == rightAnswerNumber) {
                stringBuilder.append("+");
            }
            stringBuilder.append(i);
            stringBuilder.append(") ");
            if (i == ANSWERS_COUNT - 1) {
                stringBuilder.append("Невозможно добавить одну последовательность");
            } else {
                stringBuilder.append(answers.get(i));
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private BitSequence getElementToAdd(final List<BitSequence> sequences) {
        BitSequence elementToAdd = null;
        for (int i = 0; i < sequences.size(); i++) {
            for (int j = i; j < sequences.size(); j++) {
                BitSequence sum = sequences.get(i).addModTwo(sequences.get(j));
                if (!sequences.contains(sum)) {
                    if (elementToAdd == null) {
                        elementToAdd = sum;
                    } else {
                        if (!elementToAdd.equals(sum)) {
                            return null;
                        }
                    }
                }
            }
        }
        return elementToAdd;
    }

}
