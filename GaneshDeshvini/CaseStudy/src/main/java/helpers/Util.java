package helpers;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by root on 7/1/16.
 */
public class Util {
    /**
     * convert Set to string
     *
     * @param s
     * @return
     */
    public static String convertSetToString(Set s) {
        return isCollectionValid(s) ? s.toString() : "";
    }

    /**
     * check if collections is valid
     *
     * @param c
     * @return
     */
    public static boolean isCollectionValid(Collection c) {
        return c != null && !c.isEmpty();
    }

    /**
     * check if map is valid
     *
     * @param m
     * @return
     */
    public static boolean isMapValid(Map m) {
        return m != null && !m.isEmpty();
    }

    /**
     * check if string is valid
     *
     * @param s
     * @return
     */
    public static boolean isStringValid(String s) {
        return s != null && !s.isEmpty();
    }
}
