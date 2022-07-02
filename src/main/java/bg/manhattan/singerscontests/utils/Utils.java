package bg.manhattan.singerscontests.utils;

import java.util.Random;

public class Utils {
    public static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }
}
