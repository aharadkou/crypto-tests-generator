package com.gsu;

import com.gsu.testgenerator.TestGenerator;
import com.gsu.testgenerator.TestGeneratorFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public final class Main {

    private static final int DEFAULT_TEST_COUNT = 10;

    private static final String TEST_DIR = "_TESTS/";

    private Main() {

    }

    public static void main(String[] args) {
        int testCount;
        if (args.length == 0) {
            testCount = DEFAULT_TEST_COUNT;
        } else {
            testCount = Integer.valueOf(args[0]);
        }
        StringBuilder menu = new StringBuilder();
        menu.append("Выберите номер задания:").append(System.lineSeparator());
        menu.append("0. Сгенерировать сразу все тесты").append(System.lineSeparator());
        TestGeneratorFactory.getGeneratorDescriptions()
                .forEach(description -> menu.append(description).append(System.lineSeparator()));
        menu.append("-1. Выход").append(System.lineSeparator());
        try(final Scanner scan = new Scanner(System.in)) {
            while(true) {
                try {
                    System.out.print(menu);
                    System.out.print("Введите номер пункта в меню -> ");
                    int next = scan.nextInt();
                    if (next == -1) {
                        System.exit(0);
                    } else if (next == 0) {
                        Set<TestGenerator> generators = TestGeneratorFactory.getGenerators();
                        int i = 1;
                        for(final TestGenerator generator: generators) {
                            printTest(i++, generator, testCount);
                        }
                    } else if (TestGeneratorFactory.supports(next)){
                        TestGenerator generator = TestGeneratorFactory.getTestGenerator(next);
                        printTest(next, generator, testCount);
                    } else {
                        continue;
                    }
                    System.out.println("Задания сгенерированы, их можно найти в папке " + TEST_DIR);
                } catch (InputMismatchException ex) {
                    scan.next();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    private static void printTest(final int taskNumber,
                                  final TestGenerator generator,
                                  final int testCount) throws IOException{
        new File(TEST_DIR).mkdir();
        try (PrintWriter pw = new PrintWriter(String.format("%stests%d.txt", TEST_DIR, taskNumber))) {
            pw.print(generator.generateTest(testCount));
        }
    }
}
