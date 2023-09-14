package org.example.stringRefactor.Service;

public class Palindrome implements StringRefactor {
    StringRefactor textReverse = new TextReverse();

    @Override
    public String getInfo() {
        return "7 - Sprawdź czy tekst jest palindromem";
    }

    @Override
    public String run(String text) {
        boolean isPalindrome = text.equalsIgnoreCase(textReverse.run(text).toLowerCase());
        if (isPalindrome) {
            return "Tekst jest palindromem";
        } else
            return "Tekst nie jest palindromem";
    }
}
