package sample.client;

import java.util.concurrent.ThreadLocalRandom;

public class Test {
    public static void main(String[] args) {
        String[] ch = "a:1:b:2:c:3;chat;B".split(";");
        for (String s :
                ch) {
            System.out.println(s);
        }
    }
}
