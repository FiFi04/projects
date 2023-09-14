package org.example.examResults;

import java.util.Scanner;

public class InputReaderExamResults {
    private static Scanner scanner;

    private InputReaderExamResults() {
    }

    public static Scanner getInstance() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}
