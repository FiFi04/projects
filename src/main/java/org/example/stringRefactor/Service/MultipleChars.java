package org.example.stringRefactor.Service;

public class MultipleChars implements StringRefactor{
    @Override
    public String getInfo() {
        return "13 - Powiel znaki z tekstu [np. Okno -> O-kk-nnn-oooo]";
    }

    @Override
    public String run(String text) {
        String[] letters = text.trim()
                .split("");
        String multipliedText = "";
        int counter = 0;
        for (int i = 0; i < letters.length; i++) {
            while (counter <= i) {
                multipliedText += letters[i];
                counter++;
            }
            multipliedText += "-";
            counter = 0;
        }
        return multipliedText.substring(0, multipliedText.length()-1);
    }
}
