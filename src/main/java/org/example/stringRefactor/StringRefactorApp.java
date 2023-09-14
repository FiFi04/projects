package org.example.stringRefactor;

import org.example.ScannerWrapper;

public class StringRefactorApp {
    public static void main(String[] args) {
        ScannerWrapper.logActive = true;
        final int MIN_OPTION = 0;
        final int MAX_OPTION = 13;

        StringRefactorController stringRefactorController = new StringRefactorController();
        ScannerWrapper reader = ScannerWrapper.getInstance();
        LoggerInfo logger = LoggerInfo.getInstance();

        int chosenOption;
        String input = "";
        String response;

        boolean exit = false;
        while (!exit) {
            try {
                stringRefactorController.printOptions();
                System.out.println("\nWybierz jedną z opcji:");
                chosenOption = reader.nextInt();
                if (chosenOption < MIN_OPTION || chosenOption > MAX_OPTION) {
                    throw new IndexOutOfBoundsException("Wybrano wartość spoza zakresu opcji. Spróbuj ponownie:");
                }
                if (chosenOption == 0) {
                    exit = true;
                    reader.close();
                } else {
                    System.out.println("Wprowadź tekst:");
                    input = reader.nextLine();
                }
                response = stringRefactorController.getResponse(input, chosenOption);
                System.out.println(response);
                logger.log(stringRefactorController, chosenOption, input, response);
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
                logger.logAnException(e, e.getMessage());
            }
        }
        ScannerWrapper.logActive = false;
    }
}