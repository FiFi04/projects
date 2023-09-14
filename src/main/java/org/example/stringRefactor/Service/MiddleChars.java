package org.example.stringRefactor.Service;

public class MiddleChars implements StringRefactor{
    @Override
    public String getInfo() {
        return "12 - Wyświetl środkowy znak/znaki w tekście";
    }

    @Override
    public String run(String text) {
        String[] letters = text.trim()
                .split("");
        String middleChars = "";
        if (letters.length % 2 == 0) {
            middleChars += letters[letters.length/2 - 1];
            middleChars += letters[letters.length/2];
        } else {
            middleChars += letters[letters.length/2];
        }
        return middleChars;
    }
}
