package org.example.stringRefactor.Service;

public class Exit implements StringRefactor{
    @Override
    public String getInfo() {
        return "0 - ZAKOŃCZ PROGRAM";
    }

    @Override
    public String run(String text) {
        return "Program został zamknięty";
    }
}
