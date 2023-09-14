package org.example.stringRefactor.Service;

public class CamelCase implements StringRefactor {
    @Override
    public String getInfo() {
        return "2 - Zamień tekst na CamelCale";
    }

    @Override
    public String run(String text) {
        String[] letters = text.trim().split("");
        String camelCaseText = "";
        for (int i = 0; i < letters.length; i++) {
            if (i % 2 == 0) {
                camelCaseText += letters[i].toUpperCase();
            } else {
                camelCaseText += letters[i].toLowerCase();
            }
        }
        return camelCaseText;
    }
}
