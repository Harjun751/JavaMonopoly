package io.harjun751.monopoly;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
public class RandomNumberGeneratorMock implements RandomNumberGeneratorInterface {
    private List<Integer> returnValues;
    public int generateRandomNumber(){
        int returnValue = returnValues.get(0);
        returnValues.remove(0);
        return returnValue;
    }

    public RandomNumberGeneratorMock(List<Integer> ReturnValues) {
        this.returnValues = ReturnValues;
    }
}
