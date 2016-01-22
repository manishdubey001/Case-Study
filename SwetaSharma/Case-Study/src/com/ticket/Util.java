package com.ticket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 21/1/16.
 */
public final class Util {
    public static long  Timestamp(){
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
