package util;

import java.time.LocalDateTime;

/**
 * Created by root on 5/2/16.
 */
public class test {
    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.now();
        LocalDateTime yesterday = dt.minusDays(1);

        System.out.println(dt + " | " + yesterday);
    }
}
