package com.gsu.testgenerator.impl;

import com.gsu.sequence.BitSequence;
import com.gsu.sequence.SequenceFiller;
import com.gsu.testgenerator.TestGenerator;
import com.gsu.utils.MathUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TestGeneratorCheckingMatrix implements TestGenerator {

    private static final int CODE_DIMENSION = 2;

    private static final int SEQUENCE_BITS_COUNT = 3;

    private static final int ANSWERS_COUNT = 5;

    private static final List<BitSequence> ONE_SET_BIT_SEQUENCES =
        Collections.unmodifiableList(Arrays.asList(new BitSequence(new byte[] {1, 0, 0}),
                                                   new BitSequence(new byte[] {0, 1, 0}),
                                                   new BitSequence(new byte[] {0, 0, 1})
        ));

    private final SequenceFiller sequenceFiller;

    public TestGeneratorCheckingMatrix(final SequenceFiller sequenceFiller) {
        this.sequenceFiller = sequenceFiller;
    }

    @Override
    public String generateTestTask() {
        List<BitSequence> conditions = new ArrayList<>();
        final int conditionCount = MathUtils.nextIntInclusive(4, 7);
        StringBuilder test = new StringBuilder();
        while (conditions.size() != conditionCount - 2) {
            BitSequence randomSequence = sequenceFiller.fillSequence(
                    new BitSequence(SEQUENCE_BITS_COUNT), CODE_DIMENSION);
            if (!conditions.contains(randomSequence) && !ONE_SET_BIT_SEQUENCES.contains(randomSequence)) {
                conditions.add(randomSequence);
            }
        }
        conditions.add(ONE_SET_BIT_SEQUENCES.get(0));
        conditions.add(ONE_SET_BIT_SEQUENCES.get(1));
        final int randomNumber = MathUtils.nextIntInclusive(1, 10);
        final int probability = 7;
        if (randomNumber <= probability) {
            conditions.set(conditionCount - 3, ONE_SET_BIT_SEQUENCES.get(2));
        }
        Collections.shuffle(conditions, ThreadLocalRandom.current());


        Map<BitSequence, Character> conditionMap = new LinkedHashMap<>();
        char firstSymbol = 'a';
        for (BitSequence sequence : conditions) {
            conditionMap.put(sequence, firstSymbol++);
        }
        StringBuilder oneMatrix = new StringBuilder();
        if (randomNumber <= probability) {
            ONE_SET_BIT_SEQUENCES.forEach(sequence -> oneMatrix.append(conditionMap.get(sequence)));
        }
        Collection<String> answersSet = new HashSet<>();
        while (answersSet.size() != ANSWERS_COUNT - 1) {
            List<Character> currentChar = new ArrayList<>(conditionMap.values());
            Collections.shuffle(currentChar, ThreadLocalRandom.current());
            StringBuilder currentCharString = new StringBuilder();
            currentChar.forEach(currentCharString::append);
            if (oneMatrix.toString().isEmpty() || !currentCharString.toString().endsWith(oneMatrix.toString())) {
                answersSet.add(currentCharString.toString());
            }
        }


        List<String> answersList = new ArrayList<>(answersSet);
        test.append("Даны вектора:").append(System.lineSeparator());
        conditionMap.forEach((k, v) -> test.append("    ")
                                           .append(v)
                                           .append(" = ")
                                           .append(k)
                                           .append(System.lineSeparator())
        );

        int rightAnswerNum;
        if (randomNumber <= probability) {
            rightAnswerNum = MathUtils.nextIntInclusive(0, ANSWERS_COUNT - 2);
            ONE_SET_BIT_SEQUENCES.forEach(conditionMap::remove);
            List<BitSequence> sequences = new ArrayList<>(conditionMap.keySet());
            Collections.shuffle(sequences, ThreadLocalRandom.current());
            StringBuilder rightAnswer = new StringBuilder();
            sequences.forEach(el -> rightAnswer.append(conditionMap.get(el)));
            rightAnswer.append(oneMatrix);
            answersList.set(rightAnswerNum, rightAnswer.toString());
        } else {
            rightAnswerNum = ANSWERS_COUNT - 1;
        }
        answersList.add("Невозможно построить проверочную матрицу в систематическом виде");


        test.append("Необходимо построить проверочную матрицу в систематическом виде");
        test.append(" некоторого линейного кода из этих векторов как из столбоцов");
        test.append(System.lineSeparator());
        for (int i = 0; i < answersList.size(); i++) {
            if (i == rightAnswerNum) {
                test.append("+");
            }
            test.append(i).append(") ").append(answersList.get(i)).append(System.lineSeparator());
        }
        return test.toString();
    }

}
