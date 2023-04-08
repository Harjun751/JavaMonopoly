package io.harjun751.monopoly;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomNumberGenerator implements RandomNumberGeneratorInterface {
    public int generateRandomNumber() {
        return ThreadLocalRandom.current().nextInt(1, 6 + 1);
    }
}
