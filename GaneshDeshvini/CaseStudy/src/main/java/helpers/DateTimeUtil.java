package helpers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Created by root on 14/1/16.
 */
// generally, use the Java8 classes like LocalDateTime,
// or another impelmenation like YodaTime. No need to create your own.
    //UPDATE : used JAVA 8 java.time package
public class DateTimeUtil {
    /**
     * get current time stamp in seconds
     *
     * @return
     */
    public static long getCurrentTimeStampInSeconds() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return convertToSeconds(localDateTime);
    }

    /**
     * get formatted date time
     *
     * @param timestamp
     * @return
     */
    public static String getFormattedDateTime(long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(getDateTimePatterString());
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * get date time pattern
     *
     * @return String
     */
    static String getDateTimePatterString() {
        return "yyyy MM dd h:m:s";
    }

    /**
     * convert local date time to seconds
     *
     * @param localDateTime
     * @return long
     */
    static long convertToSeconds(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }


    /**
     * minus days with custom hour, minute & second
     *
     * @param localDateTime
     * @param noOfDays
     * @param hour
     * @param minute
     * @param second
     * @return long
     */
    public static long minusDaysGetTimestamp(LocalDateTime localDateTime, int noOfDays, int hour, int minute, int second) {
        return convertToSeconds(localDateTime.minusDays(noOfDays).withHour(hour).withMinute(minute).withSecond(second));
    }

    /**
     * minus days with default hour, minute & seconds
     *
     * @param localDateTime
     * @param noOfDays
     * @return long
     */
    public static long minusDaysGetTimestamp(LocalDateTime localDateTime, int noOfDays) {
        return minusDaysGetTimestamp(localDateTime, noOfDays, 0, 0, 0);
    }

//    /**
//     * get timestamp based on hour, minute & second
//     *
//     * @param localDateTime
//     * @param hour
//     * @param minute
//     * @param second
//     * @return
//     */
//    public static long getTimeStampWithHMS(LocalDateTime localDateTime, int hour, int minute, int second) {
//        return convertToSeconds(localDateTime.withHour(hour).withMinute(minute).withSecond(second));
//    }
}
