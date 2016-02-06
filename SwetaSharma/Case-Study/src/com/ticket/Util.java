package com.ticket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by root on 21/1/16.
 */
public final class Util {
    // method names should generally start with lowercase: timestamp()

    //UPDATE
    public static LocalDateTime  timestamp(){
        // Be careful of Java's Date class. The Java 8 classes are better.
        // Also, I would not define any intermediate variables here--return
        // the value in one statement if you don't have a reason to declare
        // named variables.

        //UPDATE:
        return LocalDateTime.now();
    }

    //UPDATE:
    public static String getDateFormat(LocalDateTime timeStamp){
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return formatter.format(timeStamp);
    }
}
