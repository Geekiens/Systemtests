package tests.util;

import java.util.Random;

public class RandomUser {

    public static String randomUsername() {
        Random r = new Random();
        int randomInt = r.nextInt();
        return "username" + randomInt;
    }
}
