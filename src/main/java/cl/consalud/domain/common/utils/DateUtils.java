package cl.consalud.domain.common.utils;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_NO_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String DATE_FORMAT_NO_TIMEZONE_SHORT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_DATE_HUMAN = "yyyy/MM/dd";


    public static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;
    public static final DateTimeFormatter SHORT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(SHORT_DATE_FORMAT);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_NO_TIMEZONE = DateTimeFormatter.ofPattern(DATE_FORMAT_NO_TIMEZONE);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_NO_TIMEZONE_SHORT = DateTimeFormatter.ofPattern(DATE_FORMAT_NO_TIMEZONE_SHORT);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_DATE_HUMAN = DateTimeFormatter.ofPattern(DATE_FORMAT_DATE_HUMAN);


    public static ZonedDateTime getTimestamp() {
        return ZonedDateTime.now(Clock.system(ZoneId.of("Europe/Berlin")));
    }

    public static ZonedDateTime getChileTimeStamp() {
        return ZonedDateTime.now(Clock.system(ZoneId.of("America/Santiago")));
    }

    public static String getTimestampString() {
        return TIMESTAMP_FORMATTER.format(getTimestamp());
    }

    public static String getChileTimestampString() {
        return TIMESTAMP_FORMATTER.format(getChileTimeStamp());
    }

    public static ZonedDateTime convertToChile(ZonedDateTime zonedDateTime) {
        var instant = zonedDateTime.toInstant();
        return ZonedDateTime.ofInstant(instant, ZoneId.of("America/Santiago"));
    }


    public static void main(String[] args) {
        var tp = getTimestamp();
        tp = convertToChile(tp);
        System.out.println(tp.format(TIMESTAMP_FORMATTER));
    }

}
