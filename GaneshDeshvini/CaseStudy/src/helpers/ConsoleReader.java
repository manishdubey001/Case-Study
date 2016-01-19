package helpers;

import java.util.Scanner;

/**
 * Created by root on 14/1/16.
 */
public class ConsoleReader {
//    private static Scanner scanner = new Scanner(System.in);
//
//    private ConsoleReader() {
//    }

    public static Scanner getInstance() {
        return new Scanner(System.in);
    }
}
