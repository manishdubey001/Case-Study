package com.ticket.util;

/**
 * Created by root on 3/2/16.
 */
public class Utility {
    public static boolean isStringValid(String s) {
        return s != null && !s.isEmpty();
    }

    public static boolean isObjNull(Object obj){  return obj!=null;  }
}
