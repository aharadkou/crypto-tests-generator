package com.gsu.testgenerator.impl;

import com.gsu.testgenerator.TestGenerator;
import com.gsu.utils.MathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestGeneratorCodeBCH implements TestGenerator {

    @Override
    public String generateTestTask() {
        StringBuilder test = new StringBuilder();
        final String answerPattern = "q=%d n=%d k=%d";
        List<String> answers = new ArrayList<>();
        int q = MathUtils.nextIntInclusive(2, 4);
        int m = MathUtils.nextIntInclusive(3, 5);
        int rightN = (int) Math.pow(q, m) - 1;
        int t = MathUtils.nextIntInclusive(1, 2);
        int rightK = rightN - t * m;
        String rightAnswer = String.format(answerPattern, q, rightN, rightK);
        answers.add(rightAnswer);
        answers.add(String.format(answerPattern, q, rightN + 1, rightK));
        answers.add(String.format(answerPattern, q, rightN - 1, rightK));
        answers.add(String.format(answerPattern, q, rightK, rightN));
        answers.add(String.format(answerPattern, rightN, rightK, q));
        Collections.shuffle(answers);
        test.append("Определить при каких q, n и k существует (n, k) БЧХ-код над полем GF(q)");
        test.append(System.lineSeparator());
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).equals(rightAnswer)) {
                test.append("+");
            }
            test.append(i).append(") ").append(answers.get(i)).append(System.lineSeparator());
        }
        return test.toString();
    }
}
