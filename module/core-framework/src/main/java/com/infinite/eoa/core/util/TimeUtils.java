package com.infinite.eoa.core.util;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;

/**
 * @author hx on 16-7-27.
 */
public abstract class TimeUtils {
    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;

    public static boolean isAfterCurrentTime(long time) {
        return System.currentTimeMillis() - time < 0;
    }

    public static boolean isBeforeCurrentTime(long time) {
        return System.currentTimeMillis() - time > 0;
    }

    public static long getAfterCurrentTime(TimeUnit unit, int count) {
        long increment = getIncreament(unit);
        return System.currentTimeMillis() + increment * count;
    }

    private static long getIncreament(TimeUnit unit) {
        switch (unit) {
            case SECONDS:
                return SECOND;
            case MINUTES:
                return MINUTE;
            case HOURS:
                return HOUR;
            case DAYS:
                return DAY;
            default:
                return 1;
        }
    }

    public static long getMinMillsOfDay(String day) {
        return new DateTime(day).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).getMillis();
    }

    public static long getMaxMillsOfDay(String day) {
        return new DateTime(day).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(59).getMillis();
    }

}
