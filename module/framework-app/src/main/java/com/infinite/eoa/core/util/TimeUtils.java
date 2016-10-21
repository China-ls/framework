package com.infinite.eoa.core.util;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    public static String formatToDays(long timePeirod) {
        if (timePeirod > DAY) {
            long day = timePeirod / DAY;
            long mod = timePeirod % DAY;
            return day + "天" + formatToDays(mod);
        } else if (timePeirod > HOUR) {
            long hour = timePeirod / HOUR;
            long mod = timePeirod % HOUR;
            return hour + "小时" + formatToDays(mod);
        } else if (timePeirod > MINUTE) {
            long min = timePeirod / MINUTE;
            long mod = timePeirod % MINUTE;
            return min + "分钟" + formatToDays(mod);
        } else if (timePeirod > SECOND) {
            long sec = timePeirod / SECOND;
            long mod = timePeirod % SECOND;
            return sec + "秒" + formatToDays(mod);
        }
        return "";
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

    public static long getMinMillsOfThisMonth() {
        return getMinMillsOfMonth(new DateTime());
    }

    public static long getMinMillsOfMonth(DateTime dateTime) {
        return dateTime.withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).getMillis();
    }

    public static Date getMinUtilDateOfThisMonth() {
        return getMinUtilDateOfMonth(new DateTime());
    }

    public static Date getMinUtilDateOfMonth(DateTime dateTime) {
        return dateTime.withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate();
    }

    public static Date getMinUtilDateOfDay(DateTime dateTime) {
        return dateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate();
    }

    public static long getMinMillsOfDay(DateTime dateTime) {
        return dateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).getMillis();
    }

    public static long getMaxMillsOfDay(DateTime dateTime) {
        return dateTime.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(59).getMillis();
    }

    public static long getMinMillsOfDay(String day) {
        return new DateTime(day).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).getMillis();
    }

    public static long getMaxMillsOfDay(String day) {
        return new DateTime(day).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(59).getMillis();
    }

    public static long getTodayStartTime() {
        return new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).getMillis();
    }

    public static List<String> getMonthDays() {
        DateTime dateTime = new DateTime();
        ArrayList<String> dayList = new ArrayList<String>();
        int day = dateTime.getDayOfMonth();
        for (int i = 0; i < day; i++) {
            dayList.add(0, dateTime.minusDays(i).toString("yyyy-MM-dd"));
        }
        return dayList;
    }

    public static String[] getMonthDaysArray() {
        DateTime dateTime = new DateTime();
        String[] array = new String[30];
        for (int i = 0; i < 30; i++) {
            array[i] = dateTime.minusDays(i).toString("yyyy-MM-dd");
        }
        Arrays.sort(array);
        return array;
    }

    public static String getMonthDaysText() {
        DateTime dateTime = new DateTime();
//        int day = dateTime.getDayOfMonth();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            stringBuilder.insert(0, ",");
            stringBuilder.insert(0, dateTime.minusDays(i).toString("yyyy-MM-dd"));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(getMonthDaysText());
    }
}
