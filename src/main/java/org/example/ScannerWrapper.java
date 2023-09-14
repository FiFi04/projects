package org.example;


import org.example.stringRefactor.LoggerInfo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerWrapper {
    public static boolean logActive = false;

    private static ScannerWrapper scannerWrapper;
    private final static String INCORRECT_INPUT_MESSAGE = "Wprowadzono błędną wartość, spróbuj ponownie:";
    private static Scanner scanner = new Scanner(System.in);

    private ScannerWrapper() {
    }

    public static ScannerWrapper getInstance() {
        if (scannerWrapper == null) {
            scannerWrapper = new ScannerWrapper();
        }
        return scannerWrapper;
    }

    public int nextInt() {
        int result = 0;
        boolean exit = false;

        while (!exit) {
            try {
                result = scanner.nextInt();
                exit = true;
            } catch (InputMismatchException e) {
                System.err.println(INCORRECT_INPUT_MESSAGE);
                if (logActive) {
                    LoggerInfo.logAnException(e, INCORRECT_INPUT_MESSAGE);
                }
            } finally {
                scanner.nextLine();
            }
        }
        return result;
    }

    public String nextLine() {
        String result = "";
        boolean exit = false;

        while (!exit) {
            try {
                result = scanner.nextLine();
                exit = true;
            } catch (InputMismatchException e) {
                System.err.println(INCORRECT_INPUT_MESSAGE);
                if (logActive) {
                    LoggerInfo.logAnException(e, INCORRECT_INPUT_MESSAGE);
                }
            }
        }
        return result;
    }

    public void close() {
        scanner.close();
    }
}
