package org.example.stringRefactor.Service;

public class TextReverse implements StringRefactor{
    @Override
    public String getInfo() {
        return "3 - Zamień tekst na jego lustrzane odbicie";
    }

    @Override
    public String run(String text) {
        String[] letters = text.trim().split("");
        String reversedText = "";
        for (int i = letters.length - 1; i >= 0; i--) {
            reversedText += letters[i];
        }
        return reversedText;
    }
}
