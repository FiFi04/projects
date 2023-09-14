package org.example.stringRefactor.Service;

public class UmlautsTranslator implements StringRefactor{
    @Override
    public String getInfo() {
        return "6 - Przetłumacz na język niemiecki wprowadzony tekst [korzystając ze znaków umlaut]";
    }

    @Override
    public String run(String text) {
        String translatedText = text
                .replaceAll("ae", "ä")
                .replaceAll("oe", "ö")
                .replaceAll("ue", "ü")
                .replaceAll("Ae", "Ä")
                .replaceAll("Oe", "Ö")
                .replaceAll("Ue", "Ü");
        return translatedText;
    }
}
