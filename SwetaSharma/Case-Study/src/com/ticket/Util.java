package com.ticket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 21/1/16.
 */
public final class Util {
    // method names should generally start with lowercase: timestamp()
    public static long  Timestamp(){
        // Be careful of Java's Date class. The Java 8 classes are better.
        // Also, I would not define any intermediate variables here--return
        // the value in one statement if you don't have a reason to declare
        // named variables.
        Date date = new Date();
        long time = date.getTime();
        return time;
    }

    public static String getDateFormat(long timeStamp){
        Date date = new Date(timeStamp);
        DateFormat formatter = new  SimpleDateFormat("dd MMMM yyyy, hh:mm:ss a");
        return formatter.format(date);
    }
}
