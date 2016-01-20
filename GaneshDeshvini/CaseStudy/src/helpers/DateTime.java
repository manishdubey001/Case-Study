package helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by root on 14/1/16.
 */
// generally, use the Java8 classes like LocalDateTime,
// or another impelmenation like YodaTime. No need to create your own.
public class DateTime {
    /**
     * get current time stamp in seconds(GMT)
     *
     * @return
     */
    public static long getCurrentTimeStampInSeconds() {
        TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
        TimeZone.setDefault(gmtTimeZone);
        Calendar calendar = Calendar.getInstance(gmtTimeZone);
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * get formatted date time (IST)
     *
     * @param timestamp
     * @return
     */
    public static String getFormattedDateTime(long timestamp) {
        TimeZone istTimeZone = TimeZone.getTimeZone("IST");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(istTimeZone);
        return sdf.format(timestamp * 1000);
    }
}
