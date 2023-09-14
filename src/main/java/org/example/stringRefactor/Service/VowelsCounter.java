package org.example.stringRefactor.Service;

public class VowelsCounter implements StringRefactor {
    @Override
    public String getInfo() {
        return "8 - Zlicz samogłoski w tekście";
    }

    @Override
    public String run(String text) {
        String[] letters = text.trim()
                .toLowerCase()
                .split("");
        int vowels = 0;

        for (int i = 0; i < letters.length; i++) {
            if (letters[i].matches("[aeiou]")) {
                vowels++;
            }
        }
        return String.valueOf(vowels);
    }
}
