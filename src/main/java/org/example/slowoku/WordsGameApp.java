package org.example.slowoku;

public class WordsGameApp {
    public static void main(String[] args) {

        WordsGameController wordsGame = new WordsGameController();
        int wordLength = wordsGame.chooseWordLength();
        String word = wordsGame.drawWord(wordLength);
        wordsGame.run(word);

    }
}
