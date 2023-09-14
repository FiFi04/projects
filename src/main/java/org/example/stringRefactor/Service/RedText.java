package org.example.stringRefactor.Service;

public class RedText implements StringRefactor{
    @Override
    public String getInfo() {
        return "11 - Wyświetl tekst w kolorze czerwonym";
    }

    @Override
    public String run(String text) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        return ANSI_RED + text + ANSI_RESET;
    }
}
