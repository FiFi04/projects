package org.example.slowoku;

import org.example.ScannerWrapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordsGameController {
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_YELLOW = "\u001B[33m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RED = "\u001B[31m";
    private String[] keyWordLetters;
    private String[] givenWordLetters;
    private List<String> alphabet;
    private List<String> givenWordHistory;
    private ScannerWrapper scanner = ScannerWrapper.getInstance();
    private String incorrectFileMessage = "Nie znaleziono pliku o podanej nazwie";
    private String readExceptionMessage = "Błąd odczytu pliku";


    public WordsGameController() {
        this.alphabet = createAlphabet();
        this.givenWordHistory = new ArrayList<>();
    }

    public int chooseWordLength() {
        int wordLength = 0;
        boolean isWordLengthCorrect = false;
        while (!isWordLengthCorrect) {
                System.out.println("Podaj długość hasła do odgadnięcia:");
                wordLength = scanner.nextInt();
                if (wordLength < 5 || wordLength > 6) {
                    System.err.println("Długość słowa powinna wynosić 5 lub 6 liter.");
                    continue;
                }
                isWordLengthCorrect = true;
        }
        return wordLength;
    }

    public String drawWord(int wordLength){
        final String fiveLettersEntriesFilePath = "src/main/resources/slowokuApp/hasla5.txt";
        final String sixLettersEntriesFilePath = "src/main/resources/slowokuApp/hasla6.txt";
        String drawnWord = "";
        Random random = new Random();
        int wordIndex = random.nextInt(15);

        if (wordLength == 5) {
            try (BufferedReader entryReader = new BufferedReader(new FileReader(fiveLettersEntriesFilePath))) {
                drawnWord = entryReader.lines()
                        .toList()
                        .get(wordIndex);
            } catch (FileNotFoundException e) {
                System.err.println(incorrectFileMessage);
            } catch (IOException e) {
                System.err.println(readExceptionMessage);
            }
        } else {
            try (BufferedReader entryReader = new BufferedReader(new FileReader(sixLettersEntriesFilePath))) {
                drawnWord = entryReader.lines()
                        .toList()
                        .get(wordIndex);
            } catch (FileNotFoundException e) {
                System.err.println(incorrectFileMessage);
            } catch (IOException e) {
                System.err.println(readExceptionMessage);
            }
        }
        return drawnWord;
    }

    public void run(String word) {
        final int numberOfAttempts = 6;
        keyWordLetters = word.split("");
        boolean endGame = false;

        for (int i = 0; i < numberOfAttempts; i++) {
            if (endGame) {
                break;
            }
            boolean correctRead = false;
            while (!correctRead) {
                System.out.println("Podaj kolejne słowo (pozostała ilość prób: " + (numberOfAttempts - i) + ") ");
                String givenWord = scanner.nextLine().toLowerCase();
                correctRead = isCorrectRead(word, givenWord);
            }
            String[] unmodifiedCopyGivenLetters = Arrays.copyOf(givenWordLetters, givenWordLetters.length);
            for (int j = 0; j < keyWordLetters.length; j++) {
                if (endGame) {
                    break;
                }
                for (int k = 0; k < givenWordLetters.length; k++) {
                    if (endGame) {
                        break;
                    }
                    endGame = checkIsGameFinished();
                    if (keyWordLetters[j].equals(givenWordLetters[k]) || (ANSI_YELLOW + keyWordLetters[j] + ANSI_RESET).equals(givenWordLetters[k])) {
                        if (k == j) {
                            changeLetterToGreen(k,j);
                            break;
                        }
                        changeLetterToYellow(j, k);
                    }
                }
            }
            if (!endGame) {
                changeLetterToRedAtAlphabet(unmodifiedCopyGivenLetters);
                addGivenWordToList();
                printGivenWords();
                printAlphabet();
            }
        }
        if (!endGame) {
            System.out.println("Niestety nie udało się odgadnąć hasła. Prawidłowe hasło to: " + word);
        } else {
            System.out.println("Brawo, hasło zostało odgadnięte.");
        }
    }

    private boolean isCorrectRead(String word, String givenWord) {
        boolean correctRead;
            givenWordLetters = givenWord.split("");
            if (givenWordLetters.length != word.length()) {
                System.err.println("Nierpawidłowe hasło. Powinno ono zawierać " + word.length() + " liter.");
                correctRead = false;
            } else if (!isWordExists(word, givenWord)) {
                System.err.println("Podane słowo nie istnieje.");
                correctRead = false;
            } else {
                correctRead = true;
            }
        return correctRead;
    }

    private boolean isWordExists(String word, String givenWord) {
        final String fiveLettersWordsFilePath = "src/main/resources/slowokuApp/slowa5.txt";
        final String sixLettersWordsFilePath = "src/main/resources/slowokuApp/slowa6.txt";
        boolean wordExists = false;
        if (word.length() == 5) {
            wordExists = isWordExistsAtFile(fiveLettersWordsFilePath, givenWord);
        } else if (word.length() == 6) {
            wordExists = isWordExistsAtFile(sixLettersWordsFilePath, givenWord);
        }
        return wordExists;
    }

    private boolean isWordExistsAtFile(String fileName, String givenWord) {
        boolean wordExists = false;
        try (BufferedReader readerFiveWords = new BufferedReader(new FileReader(fileName))) {
            while (readerFiveWords.ready()) {
                if (readerFiveWords.readLine().equals(givenWord)) {
                    wordExists = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(incorrectFileMessage);
        } catch (IOException e) {
            System.err.println(readExceptionMessage);
        }
        return wordExists;
    }

    private void printAlphabet() {
        System.out.print("Użyte litery: ");
        for (String letter : alphabet) {
            System.out.print(letter + " ");
        }
        System.out.println();
    }

    private void addGivenWordToList() {
        String givenWord = "";
        for (String letter : givenWordLetters) {
            givenWord += letter;
        }
        givenWordHistory.add(givenWord);
    }

    private void printGivenWords() {
        System.out.println("Wpisane słowa:");
        for (String givenWord : givenWordHistory) {
            System.out.println(givenWord);
        }
    }

    private void changeLetterToRedAtAlphabet(String[] unmodifiedCopyGivenLetters) {
        for (int k = 0; k < givenWordLetters.length; k++) {
            for (int l = 0; l < alphabet.size(); l++) {
                if ((givenWordLetters[k].equals(unmodifiedCopyGivenLetters[k])) && alphabet.get(l).equals(unmodifiedCopyGivenLetters[k])) {
                    alphabet.set(l, ANSI_RED + alphabet.get(l) + ANSI_RESET);
                }
            }
        }
    }

    private void changeLetterToYellow(int originalLetterPosition, int givenLetterPosition) {
        givenWordLetters[givenLetterPosition] = ANSI_YELLOW + givenWordLetters[givenLetterPosition] + ANSI_RESET;
        for (int l = 0; l < alphabet.size(); l++) {
            if (alphabet.get(l).equals(keyWordLetters[originalLetterPosition])) {
                alphabet.set(l, ANSI_YELLOW + keyWordLetters[originalLetterPosition] + ANSI_RESET);
            }
        }
    }

    private void changeLetterToGreen(int originalLetterPosition, int givenLetterPosition) {
            givenWordLetters[givenLetterPosition] = ANSI_GREEN + keyWordLetters[originalLetterPosition] + ANSI_RESET;
            for (int l = 0; l < alphabet.size(); l++) {
                if (alphabet.get(l).equals(keyWordLetters[originalLetterPosition]) ||
                        (ANSI_YELLOW + keyWordLetters[originalLetterPosition] + ANSI_RESET).equals(alphabet.get(l))) {
                    alphabet.set(l, ANSI_GREEN + keyWordLetters[originalLetterPosition] + ANSI_RESET);
                }
            }
    }

    private boolean checkIsGameFinished() {
        int finishCounter = 0;
        for (int l = 0; l < keyWordLetters.length; l++) {
            if (keyWordLetters[l].equals(givenWordLetters[l])) {
                finishCounter++;
            }
        }
        return finishCounter == keyWordLetters.length;
    }

    private List<String> createAlphabet() {
        String[] alphabetArray = new String[]{"a","ą","b","c","ć","d","e","ę","f","g","h","i","j","k","l","ł","m","n",
        "ń","o","ó","p","q","r","s","ś","t","u","v","w","x","y","z","ź","ż"};
        return Arrays.asList(alphabetArray);
    }

}
