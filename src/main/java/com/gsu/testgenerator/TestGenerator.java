package com.gsu.testgenerator;

public interface TestGenerator {

    default String generateTest(final int taskCount) {
        clearBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < taskCount; i++) {
            stringBuilder.append(generateTestTask());
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    String generateTestTask();

    default void clearBuffer() {

    }

}
