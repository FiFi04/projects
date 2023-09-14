package org.example.stringRefactor.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Isogram implements StringRefactor{
    @Override
    public String getInfo() {
        return "10 - Sprawdź czy tekst jest izogramem (Posiada jedynie unikalne znaki)";
    }

    @Override
    public String run(String text) {
        String[] letters = text.trim()
                .toLowerCase()
                .split("");
        Set<String> singleLetters = new HashSet<>(Arrays.stream(letters).toList());
        boolean isIsogram = letters.length == singleLetters.size();
        if (isIsogram) {
            return "Wprowadzony tekst jest izogramem";
        } else {
            return "Wprowadzony tekst nie jest izogramem";
        }
    }
}
