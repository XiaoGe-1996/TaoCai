package com.my.aicai.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {
    private static final long ONE_DAY_MILL = 24 * 60 * 60 *1000;
    private static final int ONE_DAY_SECONDS = 24 * 60 * 60;
    private static final int ONE_HOUR_SECONDS = 60 * 60;
    private static final int ONE_MINUTE_SECONDS = 60;

    public static String getYearMonthDay() {
        return new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA).format(new Date());
    }

    public static String getYearMonthDayHourMinuteSecond() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
    }

    public static String getYearMonthDayHourMinute() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    public static String getYearMonthDayHourMinuteSecondMill() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.CHINA).format(new Date());
    }

    public static int getCurHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurMinute() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public static int getCurSecond() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.SECOND);
    }

    public static String getHourMinuteSecond() {
        return new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(new Date());
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentWeek() {
        return calWeek(getCurrentYear(), getCurrentMonth(), getCurrentDay());
    }

    public static int calWeek(int y, int m, int d) {
        int day = calPassDay(y, m, d);
        int s = (y - 1) + (y - 1) / 4 - (y - 1) / 100 + (y - 1) / 400 + day;
        return s % 7;
    }

    public static int calPassDay(int y, int m, int d) {
        int passDay = 0;
        for (int i = 0; i <= m - 1; i++) {
            passDay += calDayOfMonth(y, i);
        }
        return passDay + d;
    }

    public static int calDayOfMonth(int y, int m) {
        int day = 0;
        switch (m) {
            case 12:
                day = 31;
                break;
            case 11:
                day = 30;
                break;
            case 10:
                day = 31;
                break;
            case 9:
                day = 30;
                break;
            case 8:
                day = 31;
                break;
            case 7:
                day = 31;
                break;
            case 6:
                day = 30;
                break;
            case 5:
                day = 31;
                break;
            case 4:
                day = 30;
                break;
            case 3:
                day = 31;
                break;
            case 2:
                day = isLeapYear(y) ? 29 : 28;
                break;
            case 1:
                day = 31;
                break;
        }
        return day;
    }

    public static boolean isLeapYear(int y) {
        return y % 4 == 0 && y % 100 != 0 || y % 400 == 0;
    }

    public static String changeToTimeStr(long mill) {
        Date date = new Date(mill);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(date);
    }

    public static String getPlanTime(long mill) {
        long curMill = new Date().getTime();
        long sum = curMill + mill;
        return changeToTimeStr(sum);
    }

    public static Date getBeforeDays(int days, int y, int m, int d) {
        GregorianCalendar c = new GregorianCalendar(y+1900, m, d);
        long t = c.getTimeInMillis();
        long t1 = t-days*ONE_DAY_MILL;
        c.setTimeInMillis(t1);
        return c.getTime();
/*        Date date = new Date(y-1900, m, d);
        long t = date.getTime();
        long t1 = t-days*ONE_DAY_MILL;
        return new Date(t1);*/
    }


    public static String secondsToTimeStr(long second, String dayUnit, String hourUnit, String minuteUnit, String secondUnit) {
/*        if (second >= ONE_DAY_SECONDS) {
            int day = second / ONE_DAY_SECONDS;
            int leftSeconds = second % ONE_DAY_SECONDS;
            if (leftSeconds >= ONE_HOUR_SECONDS) {
                int hour = leftSeconds / ONE_HOUR_SECONDS;
                int llSe = leftSeconds % ONE_HOUR_SECONDS;
                if (llSe >= ONE_MINUTE_SECONDS) {
                    int minute = llSe / ONE_MINUTE_SECONDS;
                    int se = llSe % ONE_MINUTE_SECONDS;
                    return day + dayUnit + hour + hourUnit + minute + minuteUnit;
                } else {
                    if (llSe == 0) {
                        return day + dayUnit + hour + hourUnit;
                    } else {
                        return day + dayUnit + hour + hourUnit;
                    }
                }
            } else {
                if (leftSeconds >= ONE_MINUTE_SECONDS) {
                    int minute = leftSeconds / ONE_MINUTE_SECONDS;
                    int se = leftSeconds % ONE_MINUTE_SECONDS;
                    return day + dayUnit + minute + minuteUnit;
                } else {
                    if (leftSeconds == 0) {
                        return day + dayUnit;
                    } else {
                        return day + dayUnit;
                    }
                }
            }
        } else {*/
        if (second >= ONE_HOUR_SECONDS) {
            long hour = second / ONE_HOUR_SECONDS;
            return hour + hourUnit;
        } else {
            if (second >= ONE_MINUTE_SECONDS) {
                long minute = second / ONE_MINUTE_SECONDS;
                return minute + minuteUnit;
            } else {
                return second + secondUnit;
            }
        }
//        }
    }

    public static long changeToMillSecond(String timeStr, String dateSplit) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy" + dateSplit + "MM" + dateSplit + "dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        try {
            date = sdr.parse(timeStr);
            long l = date.getTime();
            Log.d("--444444---", l + "");
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDuration(String startTime, String endTime) {
        long startTimeMill = 0;
        long endTimeMill = 0;
        if (startTime.contains("/")) {
            startTimeMill = changeToMillSecond(startTime, "/") / 1000;
        } else if (startTime.contains("-")) {
            startTimeMill = changeToMillSecond(startTime, "-") / 1000;
        }
        if (endTime.contains("/")) {
            endTimeMill = changeToMillSecond(endTime, "/") / 1000;
        } else if (endTime.contains("-")) {
            endTimeMill = changeToMillSecond(endTime, "-") / 1000;
        }
        return secondsToTimeStr((endTimeMill - startTimeMill), "天", "时", "分", "秒");
    }

    public static boolean isLessThanToday(String time, int year, int month, int day) {
        if (time != null && time.length() > 0) {
            String date = time.split(" ")[0];
            Log.i("HIGHZZ", "start " + date);
            int lastStartYear = Integer.parseInt(date.split("-")[0]);
            int lastStartMoth = Integer.parseInt(date.split("-")[1]);
            int lastStartDay = Integer.parseInt(date.split("-")[2]);
            if (lastStartYear < year) {
                return true;
            } else if (lastStartYear == year) {
                if (lastStartMoth < month) {
                    return true;
                } else if (lastStartMoth == month) {
                    if (lastStartDay < day) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static boolean isLagerThanOther(String timeOne, String timeTwo) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINA);
        Date dateOne;
        Date dataTwo;
        boolean isLager = false;
        try {
            dateOne = sdr.parse(timeOne);
            dataTwo = sdr.parse(timeTwo);
            long l = dateOne.getTime();
            long l2 = dataTwo.getTime();
            isLager = l >= l2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isLager;
    }

}


