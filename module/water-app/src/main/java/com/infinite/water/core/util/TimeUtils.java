package com.infinite.water.core.util;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public abstract class TimeUtils {

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
        System.out.println( getMonthDaysText() );
    }


}
