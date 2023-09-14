package org.example.stringRefactor;

import org.example.stringRefactor.Service.*;

import java.util.ArrayList;
import java.util.List;

public class StringRefactorController {
    final static int EXIT_OPTION_INDEX = 13;
    private List<StringRefactor> stringRefactors;

    public StringRefactorController() {
        this.stringRefactors = initializeList();
    }

    public List<StringRefactor> getStringRefactors() {
        return stringRefactors;
    }

    public void printOptions() {
        System.out.println("Dostępne opcje:");
        for (StringRefactor stringRefactorImplementation : stringRefactors) {
            System.out.println(stringRefactorImplementation.getInfo());
        }
    }

    public String getResponse(String input, int chosenOption) {
        String response;

        if (chosenOption == 0) {
            response = stringRefactors.get(EXIT_OPTION_INDEX).run("");
        } else {
            response = stringRefactors.get(chosenOption - 1).run(input);
        }
        return response;
    }

    private List<StringRefactor> initializeList() {
        List<StringRefactor> stringRefactors = new ArrayList<>();
        stringRefactors.add(new DashSplit());
        stringRefactors.add(new CamelCase());
        stringRefactors.add(new TextReverse());
        stringRefactors.add(new UpperCaseText());
        stringRefactors.add(new LowerCaseText());
        stringRefactors.add(new UmlautsTranslator());
        stringRefactors.add(new Palindrome());
        stringRefactors.add(new VowelsCounter());
        stringRefactors.add(new ConsonantsCounter());
        stringRefactors.add(new Isogram());
        stringRefactors.add(new RedText());
        stringRefactors.add(new MiddleChars());
        stringRefactors.add(new MultipleChars());
        stringRefactors.add(new Exit());
        return stringRefactors;
    }
}
