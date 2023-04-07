package io.harjun751.monopoly;

import java.util.List;

public class RandomNumberGeneratorMock implements RandomNumberGeneratorInterface {
    private final List<Integer> returnValues;

    public RandomNumberGeneratorMock(List<Integer> ReturnValues) {
        this.returnValues = ReturnValues;
    }

    public int generateRandomNumber() {
        int returnValue = returnValues.get(0);
        returnValues.remove(0);
        return returnValue;
    }
}
