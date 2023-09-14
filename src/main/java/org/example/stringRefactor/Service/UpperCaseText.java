package org.example.stringRefactor.Service;

public class UpperCaseText implements StringRefactor{
    @Override
    public String getInfo() {
        return "4 - Zamień wszystkie litery na wielkie we wprowadzonym tekście";
    }

    @Override
    public String run(String text) {
        return text.toUpperCase();
    }
}
