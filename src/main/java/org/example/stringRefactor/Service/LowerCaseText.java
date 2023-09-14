package org.example.stringRefactor.Service;

public class LowerCaseText implements StringRefactor{
    @Override
    public String getInfo() {
        return "5 - Zamień wszystkie litery na małe we wprowadzonym tekście";
    }

    @Override
    public String run(String text) {
        return text.toLowerCase();
    }
}
