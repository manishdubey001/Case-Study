package helpers;

import java.util.Scanner;

/**
 * Created by root on 14/1/16.
 */
public class ConsoleReader {
    public static Scanner newInstance() {
        return new Scanner(System.in);
    }
}
