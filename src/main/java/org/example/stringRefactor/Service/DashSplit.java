package org.example.stringRefactor.Service;

public class DashSplit implements StringRefactor {

    @Override
    public String getInfo() {
        return "1 - Rozdziel tekst myślnikami [t-e-k-s-t]";
    }

    @Override
    public String run(String text) {
        String[] letters = text.trim().split("");
        String textWithDashes = "";
        for (int i = 0; i < letters.length; i++) {
            textWithDashes += letters[i] + "-";
        }
        return textWithDashes.substring(0, textWithDashes.length() - 1);
    }
}
