package org.example.stringRefactor.Service;

public class ConsonantsCounter implements StringRefactor {
    @Override
    public String getInfo() {
        return "9 - Zlicz spółgłoski w tekście";
    }

    @Override
    public String run(String text) {
        String[] letters = text.trim()
                .toLowerCase()
                .split("");
        int consonants = 0;

        for (int i = 0; i < letters.length; i++) {
            if (letters[i].matches("[bcdfghjklmnpqrstvwxyz]")) {
                consonants++;
            }
        }
        return String.valueOf(consonants);
    }
}
